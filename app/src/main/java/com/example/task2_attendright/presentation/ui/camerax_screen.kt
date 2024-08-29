package com.example.task2_attendright.presentation.ui

import android.Manifest
import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.task2_attendright.databinding.ActivityCameraxScreenBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class camerax_screen : AppCompatActivity() {
    private lateinit var binding: ActivityCameraxScreenBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
    private var imageCapture: ImageCapture? = null
    val MY_CAMERA_REQUEST_CODE: Int = 100
    val TAG = "PERMISSION_TAG"

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraxScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getPermissionCamera()
        requestPermission()
        startCamera()

        binding.switchCamera.setOnClickListener {
            cameraSelector =
                if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                else CameraSelector.DEFAULT_BACK_CAMERA
            startCamera()
        }

        binding.captureImage.setOnClickListener {
            takePhoto()
        }
    }

    fun getPermissionCamera() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                MY_CAMERA_REQUEST_CODE
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                MY_CAMERA_REQUEST_CODE
            )
        }
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(
            externalMediaDirs.firstOrNull(),
            SimpleDateFormat(
                "yyyyMMdd_HHmmss",
                Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Toast.makeText(
                        this@camerax_screen,
                        "tersimpan di ${photoFile.absolutePath}",
                        Toast.LENGTH_LONG
                    ).show()
                    val locationData = intent.getStringExtra("address")
                    binding.txtLocationData.text = locationData
                    val intent =
                        Intent(this@camerax_screen, final_clock_in_activity::class.java).apply {
                            putExtra("imagePath", photoFile.absolutePath)
                            putExtra("address2", locationData)
                        }
                    startActivity(intent)
                }

                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@camerax_screen,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "Gagal mengambil gambar: ${exc.message}", exc)
                }
            }
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewCamera.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (exc: Exception) {
                Toast.makeText(
                    this@camerax_screen,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "Gagal memunculkan kamera: ${exc.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    companion object {
        private const val TAG = "CameraActivity"
    }
}
