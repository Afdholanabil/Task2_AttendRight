package com.example.task2_attendright.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.Gender
import com.example.task2_attendright.databinding.FragmentMyProfileEditBinding
import com.example.task2_attendright.presentation.ui.activities.MyProfileActivity


class MyProfileEditFragment : Fragment() {
   private var _binding : FragmentMyProfileEditBinding? = null
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
        _binding = FragmentMyProfileEditBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterSpinner =ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item,Gender.genderListOf)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding!!.spinnerGenderEditMyProfile.adapter = adapterSpinner
        binding!!.tvCancel.setOnClickListener {
            (activity as MyProfileActivity).replaceFragmentMyProfile(MyProfileViewFragment())
        }

    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyProfileEditFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}