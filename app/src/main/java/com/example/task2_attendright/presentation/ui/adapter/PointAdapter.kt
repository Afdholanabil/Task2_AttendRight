package com.example.task2_attendright.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.Point

class PointAdapter(private val points: List<Point>) : RecyclerView.Adapter<PointAdapter.PointViewHolder>() {
    class PointViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date : TextView = view.findViewById(R.id.tv_point_tf_date_item)
        val statusPoint : TextView = view.findViewById(R.id.tv_title_poin_tf)
        val pointsurplus : TextView = view.findViewById(R.id.tv_item_point_tf_plus_minus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_poin_point, parent, false)
        return PointViewHolder(view)
    }

    override fun getItemCount(): Int {
        return points.size
    }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        val poin = points[position]
        holder.date.text = poin.date
        holder.pointsurplus.text = "+ ${poin.poin}"

    }
}