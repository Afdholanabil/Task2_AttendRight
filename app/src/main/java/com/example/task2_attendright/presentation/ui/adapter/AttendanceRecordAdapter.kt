package com.example.task2_attendright.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.AttendanceRecord

class AttendanceRecordAdapter(private val attendList : List<AttendanceRecord>) : RecyclerView.Adapter<AttendanceRecordAdapter.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.tv_date)
        val clockInTextView: TextView = view.findViewById(R.id.tv_clock_in)
        val clockOutTextView: TextView = view.findViewById(R.id.tv_clock_out)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_attendance_record, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return attendList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = attendList[position]
        holder.dateTextView.text = record.date
        holder.clockInTextView.text = record.clockIn
        holder.clockOutTextView.text = record.clockOut
    }
}