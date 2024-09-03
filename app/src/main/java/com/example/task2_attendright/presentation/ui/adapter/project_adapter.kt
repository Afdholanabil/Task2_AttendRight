package com.example.task2_attendright.presentation.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.ProjectModel
import com.example.task2_attendright.presentation.ui.activities.DetailProjectActivity
import com.example.task2_attendright.presentation.ui.customview.MyCircularProgress

class ProjectAdapter(
    private val context: Context,
    private val projectList: List<ProjectModel>,
) :
    RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateProject: TextView = view.findViewById(R.id.txt_project_date)
        val titleProject: TextView = view.findViewById(R.id.txt_project_title)
        val circularProject: MyCircularProgress =
            view.findViewById(R.id.circleProgressBar_Project_task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project_recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val projects = projectList[position]
        holder.dateProject.text = projects.dateProject
        holder.titleProject.text = projects.titleProject

        holder.circularProject.setProgress(projects.progress)
        holder.circularProject.setMaxProgress(100)
        holder.circularProject.setProgressColor(intArrayOf(Color.BLUE, Color.CYAN))
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailProjectActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = projectList.size
}