package com.example.task2_attendright.presentation.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.task2_attendright.databinding.ActivityDetailTaskBinding

class detail_task_activity : AppCompatActivity() {
    private var _binding: ActivityDetailTaskBinding? = null
    private val binding get() = _binding!!
    private val PICK_DOCUMENT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailTaskBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        binding.btnArrowBackTaskDetail.setOnClickListener { onBackPressed() }
        binding.uploadArea.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type =
                    "*/*"
                putExtra(
                    Intent.EXTRA_MIME_TYPES,
                    arrayOf(
                        "application/pdf",
                        "application/msword",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                    )
                )
            }
            startActivityForResult(intent, PICK_DOCUMENT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_DOCUMENT && resultCode == RESULT_OK && data != null) {
            val documentUri: Uri? = data.data

            documentUri?.let {
                val fileName = getFileName(it)
                binding.uploadedImage.text = fileName
                binding.uploadedImage.visibility = TextView.VISIBLE
            }
        }
    }

    private fun getFileName(uri: Uri): String {
        var fileName = ""
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            fileName = cursor.getString(nameIndex)
        }
        return fileName
    }
}