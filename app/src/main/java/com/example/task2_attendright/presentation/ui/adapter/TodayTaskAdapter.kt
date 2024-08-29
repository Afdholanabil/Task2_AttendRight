package com.example.task2_attendright.presentation.ui.adapter

import android.graphics.Color
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.TodayTask
import com.example.task2_attendright.presentation.ui.customview.MyCircularProgress

class TodayTasksAdapter(private val taskList: List<TodayTask>) :
    RecyclerView.Adapter<TodayTasksAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskTime: TextView = view.findViewById(R.id.tv_time_item_today_task)
        val taskDescription: TextView = view.findViewById(R.id.tv_item_task_today_task)
        val circularProgress : MyCircularProgress = view.findViewById(R.id.circleProgressBar_home_task)
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

        holder.circularProgress.setProgress(task.progress)
        holder.circularProgress.setMaxProgress(100)
        holder.circularProgress.setProgressColor(intArrayOf(Color.BLUE,Color.CYAN))
    }

    override fun getItemCount() = taskList.size
}

