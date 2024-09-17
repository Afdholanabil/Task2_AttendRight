package com.example.task2_attendright.presentation.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.DataProjectModel
import com.example.task2_attendright.databinding.ActivityAddProjectBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.util.Locale

class AddProjectActivity : AppCompatActivity() {
    private var _binding: ActivityAddProjectBinding? = null
    private val binding get() = _binding!!
    private var count = 0
    private val percent: String = "25"
    private lateinit var refData: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityAddProjectBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        setupDateAutoFormatting()
        setupTimeAutoFormatting()

        binding.btnArrowBackProject.setOnClickListener { onBackPressed() }

        binding.txtMinusPointProject.setOnClickListener {
            if (count >= 1000) {
                count -= 1000
                binding.txtValuePointProject.text = count.toString()
            }
        }

        binding.btnCreateAddMeet.setOnClickListener {
            saveProject()
        }

        binding.txtPlusPointProject.setOnClickListener {
            count += 1000
            binding.txtValuePointProject.text = count.toString()
        }

        binding.icUserAddProject.setOnClickListener {
            startActivity(Intent(this, AddMembersProjectActivity::class.java))
        }
    }

    private fun setupDateAutoFormatting() {
        binding.dateEdittextProject.addTextChangedListener(object : TextWatcher {
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
                binding.dateEdittextProject.setText(formatted.toString())
                binding.dateEdittextProject.setSelection(formatted.length)
                isUpdating = false
            }
        })
    }

    private fun setupTimeAutoFormatting() {
        binding.timeEdittextProject.addTextChangedListener(object : TextWatcher {
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
                binding.timeEdittextProject.setText(finalFormatted)
                binding.timeEdittextProject.setSelection(finalFormatted.length)

                isUpdating = false
            }
        })
    }

    private fun saveProject() {
        val title = binding.titleEdittextProject.text.toString()
        val description = binding.descriptionEdittextProject.text.toString()
        val date = binding.dateEdittextProject.text.toString()
        val time = binding.timeEdittextProject.text.toString()
        val point = binding.txtValuePointProject.text.toString()
        val percentData = percent
        val project = DataProjectModel(date, description, point, time, title, percentData)
        refData = Firebase.database.getReference("projects")
        refData.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val projectCount = snapshot.childrenCount
                val projectId = "projectData_${projectCount + 1}"
                refData.child(projectId).setValue(project).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(
                            Intent(
                                this@AddProjectActivity,
                                DashboardActivity::class.java
                            )
                        )
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
                            this@AddProjectActivity,
                            "Failed to save project",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@AddProjectActivity,
                    "Error: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


//    private fun saveProject() {
//        val title = binding.titleEdittextProject.text.toString()
//        val description = binding.descriptionEdittextProject.text.toString()
//        val date = binding.dateEdittextProject.text.toString()
//        val time = binding.timeEdittextProject.text.toString()
//        val point = binding.txtValuePointProject.text.toString()
//        val percentData = percent.toString()
//        val project = DataProjectModel(date, description, point , time, title, percentData)
//
//        refData.child("projectData").setValue(project).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Toast.makeText(this, "Project saved successfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Failed to save project", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
}