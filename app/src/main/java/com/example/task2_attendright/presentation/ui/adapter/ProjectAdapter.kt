package com.example.task2_attendright.presentation.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.DataProjectModel
import com.example.task2_attendright.presentation.ui.activities.DetailProjectActivity
import com.example.task2_attendright.presentation.ui.customview.MyCircularProgress
import java.text.SimpleDateFormat
import java.util.Locale

class ProjectAdapter(
    private val context: Context,
    private var projectList: List<DataProjectModel>,
) :
    RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateProject: TextView = view.findViewById(R.id.txt_project_date)
        val titleProject: TextView = view.findViewById(R.id.txt_project_title)
        val pointProject: TextView = view.findViewById(R.id.txt_title_balance)
        val circularProject: MyCircularProgress =
            view.findViewById(R.id.circleProgressBar_Project_task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project_recycler_view, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val projects = projectList[position]
        projects.dateProject?.let {
            val formattedDate = formatDate(it)
            holder.dateProject.text = formattedDate
        } ?: run {
            holder.dateProject.text = "Invalid Date"
        }
//        holder.dateProject.text = projects.dateProject
        holder.titleProject.text = projects.titleProject
        holder.pointProject.text = projects.pointProject

//        holder.circularProject.setProgress(projects.percentData!!)
//        holder.circularProject.setMaxProgress(100)
//        holder.circularProject.setProgressColor(intArrayOf(Color.BLUE, Color.CYAN))
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailProjectActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int = projectList.size

    private fun formatDate(dateString: String): String {
        try {
            val inputFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault()) // retrieve data format default
            val date = inputFormat.parse(dateString)
            val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.ENGLISH) // change format from dd/mm/yy to Day, date/Month/Year
            return date?.let { outputFormat.format(it) } ?: "Invalid Date"
        } catch (e: Exception) {
            e.printStackTrace()
            return "Invalid"
        }
    }

}