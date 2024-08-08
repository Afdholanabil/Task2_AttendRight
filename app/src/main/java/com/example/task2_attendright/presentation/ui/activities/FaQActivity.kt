package com.example.task2_attendright.presentation.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.FaQList
import com.example.task2_attendright.databinding.ActivityFaQactivityBinding
import com.example.task2_attendright.presentation.ui.adapter.ListFaQAdapter

class FaQActivity : AppCompatActivity() {

    private var _binding : ActivityFaQactivityBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityFaQactivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val faqList = listOf(
            FaQList("What is AttendRight App ?","Lorem ipsum dolor sit amet consectetur. Nec lectus augue nisl lacus ut et non volutpat. Massa est gravida et sit accumsan malesuadaporttitor. Ut netus scelerisque aliquam nisl purus ipsum varius. Nascetur in porttitor ac suscipit quis."),
            FaQList("What is AttendRight App ?","Description")
        )

        val adapter = ListFaQAdapter(faqList)
        binding?.rvListFaq?.adapter = adapter
        binding!!.rvListFaq.layoutManager = LinearLayoutManager(this)
        back()
    }

    private fun back(){
        binding!!.tvTitleFaq.setOnClickListener {
            finish()
        }
        binding!!.ivBackFaq.setOnClickListener {
            finish()
        }
    }
}