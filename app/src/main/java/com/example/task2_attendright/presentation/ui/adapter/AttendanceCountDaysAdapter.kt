package com.example.task2_attendright.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.AttendanceItemCount

class AttendanceCountDaysAdapter(private val items:List<AttendanceItemCount>):RecyclerView.Adapter<AttendanceCountDaysAdapter.AttendanceCountViewHolder>() {
    class AttendanceCountViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.titleAttendance_item)
        private val count: TextView = itemView.findViewById(R.id.count_attendance)
        private val days: TextView = itemView.findViewById(R.id.Days_attendance)

        fun bind(item: AttendanceItemCount) {
            title.text = item.title
            count.text = item.count.toString()
            days.text = item.days
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceCountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_attendance_count, parent, false)
        return AttendanceCountViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AttendanceCountViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }
}