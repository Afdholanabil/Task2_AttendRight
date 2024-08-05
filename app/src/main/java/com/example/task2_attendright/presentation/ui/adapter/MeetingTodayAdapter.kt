package com.example.task2_attendright.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.MeetingToday

class MeetingTodayAdapter(private val meetingList: List<MeetingToday>) :
    RecyclerView.Adapter<MeetingTodayAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val meetingTitle: TextView = view.findViewById(R.id.tv_item_title_meeting_today)
        val meetingLocation: TextView = view.findViewById(R.id.tv_location_meeting_today)
        val meetingType: TextView = view.findViewById(R.id.tv_clockin_out_time_home)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_meeting_today, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meeting = meetingList[position]
        holder.meetingTitle.text = meeting.title
        holder.meetingLocation.text = meeting.location
        holder.meetingType.text = meeting.type
    }

    override fun getItemCount() = meetingList.size
}