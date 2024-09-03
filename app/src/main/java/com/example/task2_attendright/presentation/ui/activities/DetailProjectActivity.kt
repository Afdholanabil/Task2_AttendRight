package com.example.task2_attendright.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2_attendright.presentation.ui.adapter.TaskAdapter
import com.example.task2_attendright.data.local.TaskModel
import com.example.task2_attendright.databinding.ActivityDetailProjectBinding

class DetailProjectActivity : AppCompatActivity() {
    private var _binding: ActivityDetailProjectBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailProjectBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

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