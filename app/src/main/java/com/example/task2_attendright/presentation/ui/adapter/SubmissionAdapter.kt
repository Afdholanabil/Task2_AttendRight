package com.example.task2_attendright.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.SubmissionList
import com.example.task2_attendright.presentation.ui.adapter.diffutil.SubmissionDiffCallback

class SubmissionAdapter : ListAdapter<SubmissionList, SubmissionAdapter.SubmissionViewHolder>(
    SubmissionDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubmissionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_submission_attendance, parent, false)
        return SubmissionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubmissionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(item)
        }
    }

    private var onItemClicked: ((SubmissionList) -> Unit)? = null

    fun setOnItemClickListener(listener: (SubmissionList) -> Unit) {
        onItemClicked = listener
    }

    class SubmissionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tv_title_submission)
        private val description: TextView = itemView.findViewById(R.id.tv_mode_submission)
        private val dateRange: TextView = itemView.findViewById(R.id.tv_date_submission)
        private val status: TextView = itemView.findViewById(R.id.tv_status_submission)
        private val cv: CardView = itemView.findViewById(R.id.cv_status_submission)
        private val icon: ImageView = itemView.findViewById(R.id.iv_status_submission)

        fun bind(item: SubmissionList) {
            title.text = item.title
            description.text = item.mode
            dateRange.text = item.date
            status.text = item.status

            when (item.status) {
                "Pending" -> {
                    status.setTextColor(ContextCompat.getColor(itemView.context, R.color.orange500))
                    cv.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.orangebg))
                    icon.setImageResource(R.drawable.rotate_right)
                }
                "Approved" -> {
                    status.setTextColor(ContextCompat.getColor(itemView.context, R.color.green500))
                    cv.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.greenbg))
                    icon.setImageResource(R.drawable.icon_check_circle)
                }
                "Declined" -> {
                    status.setTextColor(ContextCompat.getColor(itemView.context, R.color.red500))
                    cv.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.red2))
                    icon.setImageResource(R.drawable.icon_times_circle)
                }
            }
        }
    }
}
