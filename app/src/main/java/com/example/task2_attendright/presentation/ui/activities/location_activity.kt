package com.example.task2_attendright.presentation.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivityLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale

class location_activity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityLocationBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentAddress: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.btnMapsRefresh.setOnClickListener {
            getCurrentLocation()
        }

        binding.btnMapsNext.setOnClickListener {
            val intent = Intent(this, camerax_screen::class.java)
            intent.putExtra("address", currentAddress)
            startActivity(intent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setupMapUI()

        val posisi = LatLng(-7.320063, 112.7289001)
        mMap.addMarker(MarkerOptions().position(posisi).title("Test Indonesia"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posisi, 5f))
    }

    private fun setupMapUI() {
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
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

        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                val currentLatLng = LatLng(it.latitude, it.longitude)
                mMap.clear()
                mMap.addMarker(MarkerOptions().position(currentLatLng).title("Lokasi Anda"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                getAddressFromLocation(it)
            } ?: run {
                locationNotFound()
            }
        }.addOnFailureListener {
            locationNotFound()
        }
    }

    private fun getAddressFromLocation(location: Location) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address = addresses.get(0).getAddressLine(0)
                    currentAddress = address
                    binding.addressText.text = address
                    binding.imgLocationCorrect.id = R.id.img_location_correct
                    binding.imgLocationWrong.visibility = View.GONE
                    binding.wrongAddress.visibility = View.GONE

                } else {
                    locationNotFound()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Your Location is Wrong", Toast.LENGTH_SHORT).show()
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