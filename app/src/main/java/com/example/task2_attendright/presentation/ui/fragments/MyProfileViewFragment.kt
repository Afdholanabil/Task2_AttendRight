package com.example.task2_attendright.presentation.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.bumptech.glide.Glide
import com.example.task2_attendright.R
import com.example.task2_attendright.data.repository.UserRepositoryImpl
import com.example.task2_attendright.databinding.FragmentMyProfileViewBinding
import com.example.task2_attendright.presentation.ui.activities.DashboardActivity
import com.example.task2_attendright.presentation.ui.activities.MainActivity
import com.example.task2_attendright.presentation.ui.activities.MyProfileActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MyProfileViewFragment : Fragment() {

    private var _binding : FragmentMyProfileViewBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Call the back function to navigate to ProfileFragment in DashboardActivity
                back()
            }
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyProfileViewBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    private fun loadUserData() {
        val prefs = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val loggedUserId = prefs.getString("loggedUserId", null)

        if (loggedUserId != null) {
            val userDao = MainActivity.database.userDao()
            val userRepository = UserRepositoryImpl(userDao)
            CoroutineScope(Dispatchers.Main).launch {
                val user = userRepository.getUserById(loggedUserId)
                if (user != null) {
                    binding!!.tvFullNameMyProfile.text = user.name
                    binding!!.tvPositionMyProfile.text = user.role
                    binding!!.tvGenderMyProfile.text = user.gender
                    binding!!.tvBirthDateMyProfile.text = user.birthDate
                    binding!!.tvPhoneMyProfile.text = user.phone
                    binding!!.tvReligionMyProfile.text = user.religion
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserData()

        binding!!.tvEditMyProfile.setOnClickListener {
            (activity as MyProfileActivity).replaceFragmentMyProfile(MyProfileEditFragment())
        }

        Glide.with(requireContext()).load(R.drawable.image_26).circleCrop().into(binding!!.ivProfileMyProfile)

        binding!!.ivBackMyProfile.setOnClickListener {
            back()
        }
        binding!!.tvTitleMyProfile.setOnClickListener {
            back()
        }

    }

    private fun back() {
        val intent = Intent(requireContext(), DashboardActivity::class.java)
        intent.putExtra("FRAGMENT_TO_OPEN", 4)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyProfileViewFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}