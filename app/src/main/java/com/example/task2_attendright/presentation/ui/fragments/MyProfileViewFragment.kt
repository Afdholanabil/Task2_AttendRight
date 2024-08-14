package com.example.task2_attendright.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.FragmentMyProfileViewBinding
import com.example.task2_attendright.presentation.ui.activities.MyProfileActivity


class MyProfileViewFragment : Fragment() {

    private var _binding : FragmentMyProfileViewBinding? = null
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
        _binding = FragmentMyProfileViewBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Di MyProfileViewFragment
        binding!!.tvEditMyProfile.setOnClickListener {
            (activity as MyProfileActivity).replaceFragmentMyProfile(MyProfileEditFragment())
        }

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