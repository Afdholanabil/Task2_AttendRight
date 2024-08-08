package com.example.task2_attendright.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.FragmentProfileBinding
import com.example.task2_attendright.presentation.ui.activities.FaQActivity
import com.example.task2_attendright.presentation.ui.activities.LoginWEmailActivity
import com.example.task2_attendright.presentation.ui.activities.MyProfileActivity
import com.example.task2_attendright.presentation.ui.activities.PoinActivity
import com.example.task2_attendright.presentation.ui.animation.AnimationUtil


class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.tvLogout.setOnClickListener {
            val intent = Intent(requireContext(), LoginWEmailActivity::class.java )
            AnimationUtil.finishFragmentWithSlideAnimation(requireActivity(), intent)
        }

        binding!!.tvMyProfile.setOnClickListener {
            val intent = Intent(requireContext(), MyProfileActivity::class.java)
            AnimationUtil.startFragmentWithSlideAnimation(requireActivity(), intent)
        }

        binding!!.tvFaqProfile.setOnClickListener {
            val intent = Intent(requireActivity(), FaQActivity::class.java)
            AnimationUtil.startFragmentWithSlideAnimation(requireActivity(), intent)
        }

        binding!!.tvPoinProfile.setOnClickListener {
            val intent = Intent(requireActivity(), PoinActivity::class.java)
            AnimationUtil.startFragmentWithSlideAnimation(requireActivity(), intent)
        }

        binding!!.ivLogout.setOnClickListener {  }
    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}