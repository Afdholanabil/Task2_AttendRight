package com.example.task2_attendright.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.ProjectModel

class ProjectAdapter(
    private val projectList: List<ProjectModel>,
) :
    RecyclerView.Adapter<ProjectAdapter.MemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project_recycler_view, parent, false)
        return MemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val projects = projectList[position]
        holder.bind(projects)
    }

    override fun getItemCount(): Int = projectList.size

    inner class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateproject: TextView = itemView.findViewById(R.id.txt_project_date)
        private val titleproject: TextView = itemView.findViewById(R.id.txt_project_title)

        fun bind(projects: ProjectModel) {
            dateproject.text = projects.dateProject
            titleproject.text = projects.titleProject
        }
    }
}