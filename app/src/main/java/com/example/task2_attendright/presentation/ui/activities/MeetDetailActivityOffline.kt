package com.example.task2_attendright.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2_attendright.R
import com.example.task2_attendright.presentation.ui.adapter.MemberDetailAdapter
import com.example.task2_attendright.data.local.MemberDetailModel
import com.example.task2_attendright.databinding.ActivityMeetDetailOfflineBinding

class MeetDetailActivityOffline : AppCompatActivity() {
    private var _binding: ActivityMeetDetailOfflineBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMeetDetailOfflineBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        val detailProfileData = listOf(
            MemberDetailModel(R.drawable.profile_1, "Aisyah Al Humairoh", "AisyHuma6@gmail.com"),
            MemberDetailModel(
                R.drawable.profile_2, "Maulana Ahmad Reza. Abadi", "MaulanaReza@gmail.com"
            ),
            MemberDetailModel(
                R.drawable.profile_3, "Maulidah Putri Nur Fatimah", "MaulidanPutri@gmail.com"
            )
        )
        val detailAdapter = MemberDetailAdapter(this, detailProfileData)
        binding.membersDetailRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.membersDetailRecyclerView.adapter = detailAdapter

        binding.btnArrowBackMeetDetail.setOnClickListener { onBackPressed() }
    }
}