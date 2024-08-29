package com.example.task2_attendright.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2_attendright.R
import com.example.task2_attendright.presentation.ui.adapter.SelectedMembersAdapter
import com.example.task2_attendright.data.local.MemberModel
import com.example.task2_attendright.databinding.ActivityAddMeetBinding

class add_meet_activity : AppCompatActivity() {
    private var _binding: ActivityAddMeetBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddMeetBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        // accessories recycler view
        val selectedMember = intent.getParcelableArrayListExtra<MemberModel>("member")
        selectedMember?.let { members ->
            binding.rvSelectedMembers.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.rvSelectedMembers.adapter = SelectedMembersAdapter(members)
        }

        // variable spinner
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

        binding.icUserAdd.setOnClickListener {
            val intent = Intent(this, add_members_activity::class.java)
            startActivity(intent)
        }
        binding.actionBarSpinnerMeetingModeMeet.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    when (listMeetingMode[position]) {
                        "Online" -> {
                            binding.textInputLinkOnlineMeet.visibility = View.VISIBLE
                            binding.textInputLinkOfflineMeet.visibility = View.GONE
                        }

                        "Offline" -> {
                            binding.textInputLinkOfflineMeet.visibility = View.VISIBLE
                            binding.textInputLinkOnlineMeet.visibility = View.GONE
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        binding.btnCreateAddMeet.setOnClickListener {
//            val meetData = arrayListOf<MeetModel>()
            val intent = Intent(this@add_meet_activity, fragmenttTestt::class.java)
//            intent.putParcelableArrayListExtra("meet", meetData)
            startActivity(intent)

            // variable custom toast
            val inflater = layoutInflater
            val layout =
                inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))
            val toast = Toast(applicationContext)
            toast.duration = Toast.LENGTH_SHORT
            toast.view = layout
            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 50)
            toast.show()
        }
    }
}