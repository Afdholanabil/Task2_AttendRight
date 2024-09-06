package com.example.task2_attendright.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.AttendanceRecord
import com.example.task2_attendright.presentation.ui.adapter.diffutil.AttendanceRecordDiffCallback

class AttendanceRecordAdapter : ListAdapter<AttendanceRecord, AttendanceRecordAdapter.ViewHolder>(AttendanceRecordDiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.tv_date)
        val clockInTextView: TextView = view.findViewById(R.id.tv_clock_in)
        val clockOutTextView: TextView = view.findViewById(R.id.tv_clock_out)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_attendance_record, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = getItem(position)
        holder.dateTextView.text = record.date
        holder.clockInTextView.text = record.clockIn
        holder.clockOutTextView.text = record.clockOut
    }


}