package com.example.task2_attendright.presentation.ui.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.presentation.ui.adapter.diffutil.MonthDiffCallback

class MonthAdapter(private val longMonth : List<String>, private val shortMonth:List<String>,private val onMonthSelected: (String) -> Unit, private val centerMonthListener: CenterMonthListener) : ListAdapter<String, MonthAdapter.MonthViewHolder>(MonthDiffCallback()){

    var selectedPosition = -1
    class MonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val monthName : TextView = itemView.findViewById(R.id.tv_month_name)

        fun bind(month : String, isSelected : Boolean) {
            monthName.text = month
            monthName.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (isSelected) R.color.white else R.color.gray50
                )
            )
            monthName.setTypeface(null, if (isSelected) Typeface.BOLD else Typeface.NORMAL)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_month, parent, false)
        return MonthViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        val isSelected = position == selectedPosition
        val monthName = if (isSelected) longMonth[position] else shortMonth[position]
        holder.bind(monthName,isSelected)
        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
            onMonthSelected(longMonth[selectedPosition])

            (holder.itemView.parent as? RecyclerView)?.let { recyclerView ->
                val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
                layoutManager?.scrollToPositionWithOffset(
                    selectedPosition,
                    calculateCenterOffset(recyclerView, selectedPosition)
                )
            }

            (holder.itemView.parent as? RecyclerView)?.post {
                centerMonthListener.onCenterMonth(selectedPosition)
            }
        }

    }

    private fun calculateCenterOffset(recyclerView: RecyclerView, position: Int): Int {
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
        val view = layoutManager?.findViewByPosition(position)
        val itemWidth = view?.width ?: 200
        val screenWidth = recyclerView.resources.displayMetrics.widthPixels
        return (screenWidth / 2) - (itemWidth / 2)
    }

    interface CenterMonthListener {
        fun onCenterMonth(position: Int)
    }


}
