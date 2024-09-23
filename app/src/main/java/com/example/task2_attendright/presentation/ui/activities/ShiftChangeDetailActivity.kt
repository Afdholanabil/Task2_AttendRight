package com.example.task2_attendright.presentation.ui.activities

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivityShiftChangeDetailBinding
import com.example.task2_attendright.databinding.ImageDetailDialogBinding

class ShiftChangeDetailActivity : AppCompatActivity() {

    private var _binding : ActivityShiftChangeDetailBinding? = null
    private val binding get() = _binding!!

    private var selectedImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityShiftChangeDetailBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val drawableUri: Uri = Uri.parse("android.resource://" + this.packageName + "/" + R.drawable.permission)

        binding.cvShowImg.setOnClickListener {
            showImageDetailDialog(drawableUri,"IMG-... .JPG")

        }
    }

    private fun showImageDetailDialog(imageUri: Uri, imageName: String) {
        val dialogBuilder = AlertDialog.Builder(this)

        val dialogBinding = ImageDetailDialogBinding.inflate(LayoutInflater.from(this))

        val inputStream = this.contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        dialogBinding.dialogImage.setImageBitmap(bitmap)
        dialogBinding.tvImageName.text = imageName

        val dialog = dialogBuilder.setView(dialogBinding.root).create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialogBinding.closeDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}