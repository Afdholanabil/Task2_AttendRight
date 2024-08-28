package com.example.task2_attendright.presentation.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.task2_attendright.R
import com.example.task2_attendright.presentation.ui.fragments.SubmissionAttendanceFragment
import com.example.task2_attendright.presentation.ui.fragments.SubmissionLeaveReqFragment
import com.example.task2_attendright.presentation.ui.fragments.SubmissionShiftCFragment

class SubmissionFabActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_submission_fab)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container_fragment_fab_submission)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fragmentType = intent.getStringExtra("FRAGMENT_TYPE")
        if (fragmentType != null) {
            val fragment = when (fragmentType) {
                "ATTENDANCE" -> SubmissionAttendanceFragment.newInstance()
                "SHIFT_CHANGE" -> SubmissionShiftCFragment.newInstance()
                "LEAVE" -> SubmissionLeaveReqFragment.newInstance()
                else -> null
            }
            if (fragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_fragment_fab_submission, fragment)
                    .commit()
            }
        }
    }
    }
