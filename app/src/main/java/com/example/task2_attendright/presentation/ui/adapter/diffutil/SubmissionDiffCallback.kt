package com.example.task2_attendright.presentation.ui.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.task2_attendright.data.local.SubmissionList

class SubmissionDiffCallback : DiffUtil.ItemCallback<SubmissionList>() {
    override fun areItemsTheSame(oldItem: SubmissionList, newItem: SubmissionList): Boolean {
        return oldItem.title == newItem.title && oldItem.mode == newItem.mode
    }

    override fun areContentsTheSame(oldItem: SubmissionList, newItem: SubmissionList): Boolean {

        return oldItem == newItem
    }
}