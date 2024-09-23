package com.example.task2_attendright.presentation.ui.fragments

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.CustomToastBinding
import com.example.task2_attendright.databinding.FragmentSubmissionLeaveReqBinding
import com.example.task2_attendright.databinding.ImageDetailDialogBinding
import com.example.task2_attendright.presentation.ui.activities.DashboardActivity
import com.example.task2_attendright.presentation.ui.animation.AnimationUtil
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class SubmissionLeaveReqFragment : Fragment() {
  private var _binding : FragmentSubmissionLeaveReqBinding? = null
    private val binding get() = _binding

    private val PICK_IMAGE = 1
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val photoUri = result.data!!.data
                if (photoUri != null) {
                    selectedImageUri = photoUri
                    binding!!.ivShowImage.visibility = View.VISIBLE
                    loadImageWithScaling(photoUri)

                } else {
                    Log.e("SubmissionLeaveReqFragment", "Photo URI is null")
                }
            } else {
                Log.e("SubmissionLeaveReqFragment", "Image pick canceled or failed")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubmissionLeaveReqBinding.inflate(layoutInflater,container,false)
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDateRangePicker()

        binding!!.ivBackSubAttendance.setOnClickListener { back() }
        binding!!.tvBackSubAttendance.setOnClickListener { back() }
        binding!!.cvUpImg.setOnClickListener { if (checkAndRequestPermissions()) {
            openGallery()
        }
        }

        binding!!.btnSendAttendanceSub.setOnClickListener {
            showCustomToast("Success make a leave request !")
            val intent = Intent(requireContext(), DashboardActivity::class.java)
            intent.putExtra("FRAGMENT_TO_OPEN",1)
            startActivity(intent)
        }

        binding!!.ivShowImage.setOnClickListener {
            selectedImageUri?.let { uri -> showImageDetailDialog(uri,"IMG-... .JPG") }
        }

    }

    private fun showImageDetailDialog(imageUri: Uri, imageName: String) {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        val dialogBinding = ImageDetailDialogBinding.inflate(LayoutInflater.from(requireContext()))

        val inputStream = requireContext().contentResolver.openInputStream(imageUri)
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

    private fun showCustomToast(message: String) {
        context?.let { ctx ->

            val toastBinding = CustomToastBinding.inflate(LayoutInflater.from(ctx))
            toastBinding.message.text = message

            val toast = Toast(ctx)
            toast.duration = Toast.LENGTH_LONG
            toast.view = toastBinding.root

            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100)
            toast.show()
        }
    }

    private fun checkAndRequestPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                    PICK_IMAGE
                )
                false
            } else {
                true
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PICK_IMAGE
                )
                false
            } else {
                true
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        pickImageLauncher.launch(intent)
    }


    private fun loadImageWithScaling(photoUri: Uri) {
        try {
            val inputStream = requireContext().contentResolver.openInputStream(photoUri)
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }

            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream?.close()

            options.inSampleSize = calculateInSampleSize(options, 500, 500)
            options.inJustDecodeBounds = false


            val newInputStream = requireContext().contentResolver.openInputStream(photoUri)
            val bitmap = BitmapFactory.decodeStream(newInputStream, null, options)
            newInputStream?.close()

            binding!!.ivShowImage.setImageBitmap(bitmap)
            Log.d("SubmissionLeaveReqFragment", "Photo URI: $photoUri")
            Log.d("SubmissionLeaveReqFragment", "Decoded bitmap size: ${bitmap?.width} x ${bitmap?.height}")

        } catch (e: Exception) {
            Log.e("SubmissionLeaveReqFragment", "Failed to load image with scaling", e)
        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.outHeight to options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val photoUri = result.data!!.data
                if (photoUri != null) {
                    binding!!.ivShowImage.visibility = View.VISIBLE
                    binding!!.ivShowImage.setImageURI(photoUri)
                } else {
                    Log.e("SubmissionLeaveReqFragment", "Photo URI is null")
                }
            } else {
                Log.e("SubmissionLeaveReqFragment", "Image pick canceled or failed")
            }
        }

    }

    private fun back() {
        val intent = Intent(requireActivity(), DashboardActivity::class.java)
        intent.putExtra("FRAGMENT_TO_OPEN", 1)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }


    private fun setupDateRangePicker() {
        binding!!.etDate.setOnClickListener {

            val constraintsBuilder = CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select a Date Range")
                .setTheme(R.style.CustomDatePickerTheme)
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

            dateRangePicker.show(parentFragmentManager, "DATE_RANGE_PICKER")

            dateRangePicker.addOnPositiveButtonClickListener { selection ->
                val startDate = selection.first
                val endDate = selection.second
                val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

                val formattedDateRange = "${dateFormatter.format(Date(startDate!!))} - ${dateFormatter.format(Date(endDate!!))}"
                binding!!.etDate.setText(formattedDateRange)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = SubmissionLeaveReqFragment()
    }
}