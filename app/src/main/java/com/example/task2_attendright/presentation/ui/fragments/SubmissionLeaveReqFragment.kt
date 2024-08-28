package com.example.task2_attendright.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.FragmentSubmissionLeaveReqBinding


class SubmissionLeaveReqFragment : Fragment() {
  private var _binding : FragmentSubmissionLeaveReqBinding? = null
    private val bindings get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubmissionLeaveReqBinding.inflate(layoutInflater,container,false)
        return bindings!!.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = SubmissionLeaveReqFragment()
    }
}