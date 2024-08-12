package com.example.task2_attendright.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivityAddMeetBinding

class add_meet_activity : AppCompatActivity() {
    private var _binding: ActivityAddMeetBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddMeetBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        val spinnerProject: Spinner = binding.actionBarSpinnerProjectMeet
        val spinnerMeetingMode: Spinner = binding.actionBarSpinnerMeetingModeMeet

        val listProject = resources.getStringArray(R.array.project)
        val listMeetingMode = resources.getStringArray(R.array.meeting_mode)

        val adapterProject = ArrayAdapter(this, android.R.layout.simple_spinner_item, listProject)
        val adapterMeetingMode =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, listMeetingMode)

        adapterProject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterProject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerProject.adapter = adapterProject
        spinnerMeetingMode.adapter = adapterMeetingMode

        spinnerProject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        spinnerMeetingMode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        binding.btnArrowBackMeet.setOnClickListener {
            onBackPressed()
        }
    }
}