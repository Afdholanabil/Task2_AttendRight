package com.example.task2_attendright.presentation.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivityLocationOsmBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.util.Locale

class location_activity_osm : AppCompatActivity() {
    private var _binding: ActivityLocationOsmBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: MapView
    private lateinit var myLocationOverlay: MyLocationNewOverlay
    private lateinit var mapController: IMapController
    private var currentPoint: GeoPoint? = null
    private var currentAddress: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLocationOsmBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        mMap = binding.mapOsn
        initializeOSM()

        binding.btnMapsRefresh.setOnClickListener {
            getCurrentLocation()
        }

        binding.btnMapsNext.setOnClickListener {
            val intent = Intent(this, camerax_screen::class.java)
            intent.putExtra("address", currentAddress)
            startActivity(intent)
        }
    }

    private fun initializeOSM() {
        Configuration.getInstance()
            .load(this, getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE))
        mMap.setMultiTouchControls(true)

        val defaultPoint = GeoPoint(-6.2088, 106.8456)
        mapController = mMap.controller
        mapController.setCenter(defaultPoint)
        mapController.setZoom(10.0)

        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mMap)
        mMap.overlays.add(myLocationOverlay)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun requestLocationUpdate() {
        myLocationOverlay.enableMyLocation()
        myLocationOverlay.enableFollowLocation()
        myLocationOverlay.isDrawAccuracyEnabled = true

        myLocationOverlay.runOnFirstFix {
            GlobalScope.launch(Dispatchers.Main) {
                currentPoint = myLocationOverlay.myLocation
                mapController.setCenter(currentPoint)
                mapController.animateTo(currentPoint)

                val userMarker = Marker(mMap)
                userMarker.position = currentPoint
                userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                mMap.overlays.add(userMarker)
                mMap.invalidate()

                updateTextView(currentPoint!!)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateTextView(geoPoint: GeoPoint) {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(geoPoint.latitude, geoPoint.longitude, 1)

        if (addresses!!.isNotEmpty()) {
            val address = addresses?.get(0)?.getAddressLine(0)
            currentAddress = address
            binding.addressText.text = address
            binding.imgLocationCorrect.id = R.id.img_location_correct
            binding.imgLocationWrong.visibility = View.GONE
            binding.wrongAddress.visibility = View.GONE
        } else {
            locationNotFound()
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        lastLocation?.let { location ->
            currentPoint = GeoPoint(location.latitude, location.longitude)

            mMap.controller.animateTo(currentPoint)
            mMap.controller.setZoom(16.0)

            val userMarker = Marker(mMap)
            userMarker.position = currentPoint
            userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            mMap.overlays.add(userMarker)

            updateTextView(currentPoint!!)
            mMap.invalidate()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun locationNotFound() {
        binding.wrongAddress.text = R.id.wrong_address.toString()
        binding.imgLocationWrong.id = R.id.img_location_wrong
        binding.addressText.visibility = View.GONE
        binding.imgLocationCorrect.visibility = View.GONE
    }
}