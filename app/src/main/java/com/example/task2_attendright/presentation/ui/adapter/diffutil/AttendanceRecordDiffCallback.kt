package com.example.task2_attendright.presentation.ui.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.task2_attendright.data.local.AttendanceRecord

class AttendanceRecordDiffCallback : DiffUtil.ItemCallback<AttendanceRecord>() {
    override fun areItemsTheSame(oldItem: AttendanceRecord, newItem: AttendanceRecord): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AttendanceRecord, newItem: AttendanceRecord): Boolean {
        return oldItem == newItem
    }
}