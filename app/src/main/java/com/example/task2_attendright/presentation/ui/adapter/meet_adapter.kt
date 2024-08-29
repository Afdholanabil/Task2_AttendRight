package com.example.task2_attendright.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.MeetModel

class MeetAdapter(
    private val items: List<MeetModel>,
    private val listener: OnMeetClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnMeetClickListener {
        fun onMeetClick(meetModel: MeetModel)
    }

    companion object {
        private const val VIEW_TYPE_ONLINE = 1
        private const val VIEW_TYPE_OFFLINE = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].meetingModeMeet == "Online") {
            VIEW_TYPE_ONLINE
        } else {
            VIEW_TYPE_OFFLINE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val meet = items[position]
        if (holder is OnlineViewHolder) {
            holder.bindingData(meet)
            holder.itemView.setOnClickListener {
                listener.onMeetClick(meet)
            }
        } else if (holder is OfflineViewHolder) {
            holder.bindingData(meet)
            holder.itemView.setOnClickListener {
                listener.onMeetClick(meet)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ONLINE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_meet_recycler_view_online, parent, false)
            OnlineViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_meet_recycler_view_offline, parent, false)
            OfflineViewHolder(view)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class OnlineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.txt_title_member_meet)
        private val meetingMode: TextView = itemView.findViewById(R.id.txt_title_status_meet)
        private val link: TextView = itemView.findViewById(R.id.txt_title_link_meet)
        private val time: TextView = itemView.findViewById(R.id.txt_title_time_meet)

        fun bindingData(meetModel: MeetModel) {
            title.text = meetModel.titleMeet
            meetingMode.text = meetModel.meetingModeMeet
            link.text = meetModel.linkMeet
            time.text = meetModel.timeMeet
        }
    }

    inner class OfflineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.txt_title_member_meet)
        private val meetingMode: TextView = itemView.findViewById(R.id.txt_title_status_meet)
        private val link: TextView = itemView.findViewById(R.id.txt_title_link_meet)
        private val time: TextView = itemView.findViewById(R.id.txt_title_time_meet)

        fun bindingData(meetModel: MeetModel) {
            title.text = meetModel.titleMeet
            meetingMode.text = meetModel.meetingModeMeet
            link.text = meetModel.linkMeet
            time.text = meetModel.timeMeet
        }
    }
}
