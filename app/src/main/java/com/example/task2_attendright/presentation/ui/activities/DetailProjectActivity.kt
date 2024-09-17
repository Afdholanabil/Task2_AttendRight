package com.example.task2_attendright.presentation.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2_attendright.data.local.TaskModel
import com.example.task2_attendright.databinding.ActivityDetailProjectBinding
import com.example.task2_attendright.presentation.ui.adapter.TaskAdapter

class DetailProjectActivity : AppCompatActivity() {
    private var _binding: ActivityDetailProjectBinding? = null
    private val binding get() = _binding!!
    private val _value: Int = 45

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailProjectBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        setProgress(_value)
        binding.progressBar.progress = _value
        binding.txtProgressPercent.text = "$_value%"
        binding.btnArrowBackDetailProject.setOnClickListener { onBackPressed() }

        val taskList = listOf(
            TaskModel("Create User Persona"),
            TaskModel("Create User Persona"),
        )

        binding.titleAboutApps.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    Detail2ProjectActivity::class.java
                )
            )
        }

        val taskAdapter = TaskAdapter(this, taskList)
        binding.rvDataTask.layoutManager = LinearLayoutManager(this)
        binding.rvDataTask.adapter = taskAdapter

        binding.floatingButtonAddTask.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }


    }
}