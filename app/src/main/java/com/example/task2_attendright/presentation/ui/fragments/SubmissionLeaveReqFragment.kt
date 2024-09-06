package com.example.task2_attendright.presentation.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.FragmentSubmissionLeaveReqBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

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
    }

    private fun setupDatePicker() {
        binding?.etDate?.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = String.format("%02d / %02d / %04d", selectedDay, selectedMonth + 1, selectedYear)
                    binding?.etDate?.setText(formattedDate)
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }
    }

    private fun setupDateRangePicker() {
        binding!!.etDate.setOnClickListener {

            val constraintsBuilder = CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

            // Create Date Range Picker
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select a Date Range")
                .setTheme(R.style.CustomDatePickerTheme)
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

            // Show Date Range Picker
            dateRangePicker.show(parentFragmentManager, "DATE_RANGE_PICKER")

            // Set the selected date to the EditText
            dateRangePicker.addOnPositiveButtonClickListener { selection ->
                val startDate = selection.first
                val endDate = selection.second
                val dateFormatter = SimpleDateFormat("dd / MM / yyyy", Locale.getDefault())

                // Format and display the selected date range
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