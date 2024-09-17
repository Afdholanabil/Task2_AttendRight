package com.example.task2_attendright.presentation.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.MeetModel
import com.example.task2_attendright.data.local.MemberModel
import com.example.task2_attendright.databinding.ActivityAddMeetBinding
import com.example.task2_attendright.presentation.ui.adapter.SelectedMembersAdapter
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.util.Locale

class AddMeetActivity : AppCompatActivity() {
    private var _binding: ActivityAddMeetBinding? = null
    private val binding get() = _binding!!
    private lateinit var refData: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddMeetBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        setupDateAutoFormatting()
        setupTimeAutoFormatting()


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
                val selectedMeetingMode = listMeetingMode[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        binding.btnArrowBackMeet.setOnClickListener {
            onBackPressed()
        }

        binding.icUserAdd.setOnClickListener {
            val intent = Intent(this, AddMembersActivity::class.java)
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
            saveMeet()
//            // variable custom toast
//            val inflater =
//                application.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//            val layout =
//                inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))
//            val toast = Toast(applicationContext)
//            toast.duration = Toast.LENGTH_SHORT
//            toast.view = layout
//            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 50)
//            toast.show()
        }
    }

    private fun setupDateAutoFormatting() {
        binding.dateEdittextMeet.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false
            private val dividerCharacter = '/'
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true
                var input = s.toString().replace("[^\\d]".toRegex(), "")
                if (input.length > 6) {
                    input = input.substring(0, 6)
                }

                // Tambahan Auto Format day/month/year
                val formatted = StringBuilder()
                for (i in input.indices) {
                    formatted.append(input[i])
                    if ((i == 1 || i == 3) && i < input.length - 1) {
                        formatted.append(dividerCharacter)
                    }
                }
                binding.dateEdittextMeet.setText(formatted.toString())
                binding.dateEdittextMeet.setSelection(formatted.length)
                isUpdating = false
            }
        })
    }

    private fun setupTimeAutoFormatting() {
        binding.timeEdittextMeet.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true
                var input = s.toString().replace("[^\\d]".toRegex(), "")
                if (input.length < 4) {
                    isUpdating = false
                    return
                }
                if (input.length > 4) {
                    input = input.substring(0, 4)
                }
                val hours = input.substring(0, 2).toIntOrNull() ?: 0
                val minutes = input.substring(2, 4).toIntOrNull() ?: 0
                val amPm: String
                var displayHours = hours

                if (hours > 12) {
                    displayHours -= 12
                    amPm = "PM"
                } else {
                    if (hours == 0) displayHours = 12
                    amPm = "AM"
                }

                // Format final menjadi hh:mm AM/PM
                val finalFormatted =
                    String.format(Locale.getDefault(), "%02d:%02d %s", displayHours, minutes, amPm)
                binding.timeEdittextMeet.setText(finalFormatted)
                binding.timeEdittextMeet.setSelection(finalFormatted.length)

                isUpdating = false
            }
        })
    }

    private fun saveMeet() {
        val title = binding.titleEdittextMeet.text.toString()
        val meetingMode = binding.actionBarSpinnerMeetingModeMeet.selectedItem.toString()
        val meetingVia = binding.edittextLinkOnlineMeet.text.toString()
        val date = binding.dateEdittextMeet.text.toString()
        val time = binding.timeEdittextMeet.text.toString()
        val description = binding.descriptionEdittextMeet.text.toString()
        val meet = MeetModel(title, meetingMode, meetingVia, date, time, description)
        refData = Firebase.database.getReference("meets")
        refData.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val meetCount = snapshot.childrenCount
                val meetId = "meetData_${meetCount + 1}"
                refData.child(meetId).setValue(meet).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this@AddMeetActivity, DashboardActivity::class.java))
                        // custom toast
                        val inflater =
                            application.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                        val layout =
                            inflater.inflate(
                                R.layout.custom_toast,
                                findViewById(R.id.custom_toast_container)
                            )
                        val toast = Toast(applicationContext)
                        toast.duration = Toast.LENGTH_SHORT
                        toast.view = layout
                        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 50)
                        toast.show()
                    } else {
                        Toast.makeText(
                            this@AddMeetActivity,
                            "Failed to save project",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@AddMeetActivity,
                    "Error: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}