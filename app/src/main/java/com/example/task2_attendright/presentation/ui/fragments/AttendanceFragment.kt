package com.example.task2_attendright.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.FragmentAttendanceBinding

class AttendanceFragment : Fragment() {
    private var _binding : FragmentAttendanceBinding? = null
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
        _binding = FragmentAttendanceBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AttendanceFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}