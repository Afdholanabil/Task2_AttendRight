package com.example.task2_attendright.presentation.ui.fragments

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.Gender
import com.example.task2_attendright.databinding.CustomToastBinding
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

        ViewCompat.setOnApplyWindowInsetsListener(binding!!.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, imeInsets.bottom)

            insets
        }

        val adapterSpinner =ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item,Gender.genderListOf)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding!!.spinnerGenderEditMyProfile.adapter = adapterSpinner
        binding!!.tvCancel.setOnClickListener {
            (activity as MyProfileActivity).supportFragmentManager.popBackStack()
        }

        Glide.with(requireContext()).load(R.drawable.image_26).circleCrop().into(binding!!.ivProfileMyProfile)

        binding!!.btnSaveEditMyProfile.setOnClickListener {
            showCustomToast("Changes Saved !")
            (activity as MyProfileActivity).supportFragmentManager.popBackStack()
        }

    }

    private fun showCustomToast(message: String) {
        context?.let { ctx ->

            val toastBinding = CustomToastBinding.inflate(LayoutInflater.from(ctx))
            toastBinding.message.text = message

            val toast = Toast(ctx)
            toast.duration = Toast.LENGTH_LONG
            toast.view = toastBinding.root

            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100)
            toast.show()
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