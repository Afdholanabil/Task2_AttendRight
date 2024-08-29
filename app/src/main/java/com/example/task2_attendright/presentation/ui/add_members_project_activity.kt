package com.example.task2_attendright.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2_attendright.R
import com.example.task2_attendright.adapter.MemberAdapter
import com.example.task2_attendright.data.local.MemberModel
import com.example.task2_attendright.databinding.ActivityAddMembersProjectBinding

class add_members_project_activity : AppCompatActivity() {
    private var _binding: ActivityAddMembersProjectBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddMembersProjectBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        val members = listOf(
            MemberModel(R.drawable.profile_1, "Aisyah Al Humairoh", "AisyHuma6@gmail.com", false),
            MemberModel(R.drawable.profile_1, "Aisyah Al Humairoh", "AisyHuma6@gmail.com", false),
            MemberModel(R.drawable.profile_1, "Aisyah Al Humairoh", "AisyHuma6@gmail.com", false),
            MemberModel(
                R.drawable.profile_2, "Maulana Ahmad Reza. Abadi", "MaulanaReza@gmail.com", false
            ),
            MemberModel(
                R.drawable.profile_3, "Maulidah Putri Nur Fatimah", "MaulidanPutri@gmail.com", false
            ),
            MemberModel(
                R.drawable.profile_3, "Maulidah Putri Nur Fatimah", "MaulidanPutri@gmail.com", false
            ),
            MemberModel(R.drawable.profile_4, "Zayidul Ahdan", "Ahdan99@gmail.com", false),
            MemberModel(R.drawable.profile_4, "Zayidul Ahdan", "Ahdan99@gmail.com", false),
            MemberModel(R.drawable.profile_4, "Zayidul Ahdan", "Ahdan99@gmail.com", false),
        )

        binding.btnArrowBackAddMembers.setOnClickListener { onBackPressed() }
        val selectedMembers = arrayListOf<MemberModel>()
        val rvMembers = binding.membersRecyclerView
        rvMembers.layoutManager = LinearLayoutManager(this)
        val memberAdapter = MemberAdapter(members) { member, isSelected ->
            if (isSelected) {
                selectedMembers.add(member)
            } else {
                selectedMembers.remove(member)
            }
        }
        binding.membersRecyclerView.adapter = memberAdapter
        binding.checkboxSelectAllMembers.setOnCheckedChangeListener { _, isChecked ->
            memberAdapter.allSelected(isChecked)
        }

        binding.txtSaveAddMembers.setOnClickListener { }
    }
}