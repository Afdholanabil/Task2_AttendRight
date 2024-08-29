package com.example.task2_attendright.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.Point
import com.example.task2_attendright.databinding.FragmentMonthBinding
import com.example.task2_attendright.presentation.ui.adapter.PointAdapter
import java.time.Month


class MonthFragment : Fragment() {

    private var _binding : FragmentMonthBinding? = null
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
        _binding = FragmentMonthBinding.inflate(layoutInflater,container,false)
        val rv = binding!!.recyclerView
        rv.layoutManager = LinearLayoutManager(context)
        val monthIndex = arguments?.getInt("monthIndex") ?: 0
        rv.adapter = PointAdapter(getPointsForMonth(monthIndex))

        return binding!!.root
    }

    companion object {

        @JvmStatic
        fun newInstance(monthIndex : Int) : MonthFragment {
            val fragment = MonthFragment()
            val args = Bundle()
            args.putInt("monthIndex",monthIndex)
            fragment.arguments = args
            return fragment
        }

    }

    private fun getPointsForMonth(monthIndex: Int): List<Point> {

        return listOf(
            Point("20 Agustus 2024", 2000),
            Point("20 Agustus 2024", 2000)
        )
    }
}