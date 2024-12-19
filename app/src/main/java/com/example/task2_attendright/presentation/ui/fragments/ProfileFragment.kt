package com.example.task2_attendright.presentation.ui.fragments

import ClearSessionUseCase
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.datastore.SessionDataStore
import com.example.task2_attendright.data.repository.SessionRepositoryImpl
import com.example.task2_attendright.data.repository.UserRepositoryImpl
import com.example.task2_attendright.databinding.FragmentProfileBinding
import com.example.task2_attendright.presentation.ui.activities.FaQActivity
import com.example.task2_attendright.presentation.ui.activities.LoginWEmailActivity
import com.example.task2_attendright.presentation.ui.activities.MainActivity
import com.example.task2_attendright.presentation.ui.activities.MyProfileActivity
import com.example.task2_attendright.presentation.ui.activities.PoinActivity
import com.example.task2_attendright.presentation.ui.animation.AnimationUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private fun loadUserData() {
        val prefs = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val loggedUserId = prefs.getString("loggedUserId", null)

        if (loggedUserId != null) {
            val userDao = MainActivity.database.userDao()
            val userRepository = UserRepositoryImpl(userDao)
            CoroutineScope(Dispatchers.Main).launch {
                val user = userRepository.getUserById(loggedUserId)
                if (user != null) {
                    binding.tvNamaProfile.text = user.name
                    binding.tvRoleProfileProfile.text = "${user.userId} - ${user.role}"
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserData()

        binding.tvLogout.setOnClickListener {
            lifecycleScope.launch {
                val sessionRepo = SessionRepositoryImpl(SessionDataStore(requireContext()))
                val clearSessionUseCase = ClearSessionUseCase(sessionRepo)
                clearSessionUseCase()

                // Setelah logout, check rememberMe. Jika false, tidak ada email disimpan (sudah dihapus saat login)
                // Jika true, email tetap ada di prefs.
                // Langsung ke login.
                val intent = Intent(requireContext(), LoginWEmailActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        binding.tvMyProfile.setOnClickListener {
            val intent = Intent(requireContext(), MyProfileActivity::class.java)
            AnimationUtil.startFragmentWithSlideAnimation(requireActivity(), intent)
        }

        binding.tvFaqProfile.setOnClickListener {
            val intent = Intent(requireContext(), FaQActivity::class.java)
            AnimationUtil.startFragmentWithSlideAnimation(requireActivity(), intent)
        }

        binding.tvPoinProfile.setOnClickListener {
            val intent = Intent(requireContext(), PoinActivity::class.java)
            AnimationUtil.startFragmentWithSlideAnimation(requireActivity(), intent)
        }

        Glide.with(requireContext()).load(R.drawable.image_26).circleCrop().into(binding.ivProfileProfile)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
