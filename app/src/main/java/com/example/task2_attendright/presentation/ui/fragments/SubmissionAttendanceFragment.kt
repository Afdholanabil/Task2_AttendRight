package com.example.task2_attendright.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.FragmentSubmissionAttendanceBinding


class SubmissionAttendanceFragment : Fragment() {
   private var _bindings : FragmentSubmissionAttendanceBinding? = null
    private val bindings get() = _bindings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _bindings = FragmentSubmissionAttendanceBinding.inflate(layoutInflater,container,false)
        return bindings!!.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SubmissionAttendanceFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}