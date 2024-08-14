package com.example.task2_attendright.presentation.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.FragmentAttendanceBinding
import com.example.task2_attendright.presentation.ui.adapter.AttendanceTabAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AttendanceFragment : Fragment() {
    private var _binding : FragmentAttendanceBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAttendanceBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.buttonYear.setOnClickListener {
            showYearPickerDialog(binding!!.buttonYear)
        }

        val tabLayoutAdapter = AttendanceTabAdapter(requireActivity())
        val viewPagerTab : ViewPager2 =binding!!.vpTabAttendance
        viewPagerTab.adapter = tabLayoutAdapter
        val tabs : TabLayout = binding!!.tabLayoutAttendance

        val titleTab = listOf("Attendance","Submission")
        TabLayoutMediator(tabs,viewPagerTab){tab,ps ->
            tab.text = titleTab[ps]
        }.attach()

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AttendanceFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun showYearPickerDialog(button: MaterialButton) {
        val years = (2000..2100).toList().map { it.toString() }.toTypedArray()
        var selectedYear = button.text.toString()

        AlertDialog.Builder(requireActivity())
            .setTitle("Pilih Tahun")
            .setSingleChoiceItems(years, years.indexOf(selectedYear)) { dialog, which ->
                selectedYear = years[which]
            }
            .setPositiveButton("OK") { dialog, _ ->
                button.text = selectedYear
                dialog.dismiss()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}