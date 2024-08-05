package com.example.task2_attendright.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.TodayTask

class TodayTasksAdapter(private val taskList: List<TodayTask>) :
    RecyclerView.Adapter<TodayTasksAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskTime: TextView = view.findViewById(R.id.tv_time_item_today_task)
        val taskDescription: TextView = view.findViewById(R.id.tv_item_task_today_task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_today_tasks, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskList[position]
        holder.taskTime.text = task.time
        holder.taskDescription.text = task.description
    }

    override fun getItemCount() = taskList.size
}

