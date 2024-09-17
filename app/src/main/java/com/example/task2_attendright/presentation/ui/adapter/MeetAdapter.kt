package com.example.task2_attendright.presentation.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.MeetModel
import com.example.task2_attendright.presentation.ui.activities.MeetDetailActivityOnline
import java.text.SimpleDateFormat
import java.util.Locale

class MeetAdapter(
    private val context: Context,
    private var meetList: List<MeetModel>,
//    private val listener: OnMeetClickListener
) :
    RecyclerView.Adapter<MeetAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = itemView.findViewById(R.id.txt_title_member_meet)
        var meetingMode: TextView = itemView.findViewById(R.id.txt_title_status_meet)
        var meetingVia: TextView = itemView.findViewById(R.id.txt_title_link_meet)
        var time: TextView = itemView.findViewById(R.id.txt_title_time_meet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meet_recycler_view_online, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meets = meetList[position]
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, MeetDetailActivityOnline::class.java))
        }
        holder.title.text = meets.titleMeet
        holder.meetingMode.text = meets.meetingModeMeet
        holder.meetingVia.text = meets.meetingViaMeet
        holder.time.text = meets.timeMeet
    }

    override fun getItemCount(): Int = meetList.size

    private fun formatDate(dateString: String): String {
        try {
            val inputFormat =
                SimpleDateFormat("dd/MM/yy", Locale.getDefault()) // retrieve data format default
            val date = inputFormat.parse(dateString)
            val outputFormat = SimpleDateFormat(
                "EEEE, dd MMMM yyyy",
                Locale.ENGLISH
            ) // change format from dd/mm/yy to Day, date/Month/Year
            return date?.let { outputFormat.format(it) } ?: "Invalid Date"
        } catch (e: Exception) {
            e.printStackTrace()
            return "Invalid"
        }
    }
}
