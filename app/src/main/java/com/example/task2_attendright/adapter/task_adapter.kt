package com.example.task2_attendright.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.TaskModel
import com.example.task2_attendright.presentation.ui.detail_detail_project_activity
import com.example.task2_attendright.presentation.ui.detail_task_activity

class TaskAdapter(
    private val context: Context,
    private val projectList: List<TaskModel>,
) :
    RecyclerView.Adapter<TaskAdapter.MemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_add_task_project, parent, false)
        return MemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val projects = projectList[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(context, detail_task_activity::class.java)
            context.startActivity(intent)
        }
        holder.bindingData(projects)
    }

    override fun getItemCount(): Int = projectList.size

    inner class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskTitleProject: TextView = itemView.findViewById(R.id.title_task_data)

        fun bindingData(task: TaskModel) {
            taskTitleProject.text = task.taskTitleProject
        }
    }
}