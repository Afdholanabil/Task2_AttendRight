package com.example.task2_attendright.presentation.ui

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task2_attendright.databinding.ActivityFinalClockInBinding
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

        val imageBitmap = intent.getParcelableExtra<Bitmap>("imageBitmap")
        binding.imageView.setImageBitmap(imageBitmap)

        binding.btnMapsBack.setOnClickListener {
            val intent = Intent(this, final_clock_in_activity::class.java)
            startActivity(intent)
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