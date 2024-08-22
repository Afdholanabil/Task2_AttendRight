package com.example.task2_attendright.presentation.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task2_attendright.databinding.ActivityFinalClockInBinding
import com.example.task2_attendright.util.getCorrectlyOrientedBitmap
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class final_clock_in_activity : AppCompatActivity() {
    private lateinit var binding: ActivityFinalClockInBinding
    val currentTime = getCurrentTimeStamp()
    val currentDate = getCurrentDate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalClockInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtClockNow.text = currentTime
        binding.txtCurrentDay.text = currentDate

        val locationText = intent.getStringExtra("address2")
        binding.addressText.text = locationText

        val imagePath = intent.getStringExtra("imagePath")
        val imageBitmap = imagePath?.let { getCorrectlyOrientedBitmap(it) }
        binding.imageView.setImageBitmap(imageBitmap)

        binding.btnMapsBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnMapsNext.setOnClickListener {
            val intent2 = Intent(this, success_information_clock_in::class.java)
            intent2.putExtra("date", currentDate)
            intent2.putExtra("time", currentTime)
            startActivity(intent2)
        }
    }
}

private fun getCurrentTimeStamp(): String {
    val dateFormat = SimpleDateFormat("HH:mm:ss", Locale("id", "ID"))
    return dateFormat.format(Date())
}

private fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID"))
    return dateFormat.format(Date())
}