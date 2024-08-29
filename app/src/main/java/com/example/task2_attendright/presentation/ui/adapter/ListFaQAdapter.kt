package com.example.task2_attendright.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.FaQList

class ListFaQAdapter(private val faqList : List<FaQList>) : RecyclerView.Adapter<ListFaQAdapter.ViewHolder>(){
    private val expandedPositionSet: MutableSet<Int> = mutableSetOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val faqTitle : TextView = view.findViewById(R.id.tv_title_faq)
        val faqDesc : TextView = view.findViewById(R.id.tv_desc_faq)
        val seeDetail : ImageView = view.findViewById(R.id.iv_see_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_faq, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return faqList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val faq = faqList[position]
        holder.faqTitle.text = faq.name
        holder.faqDesc.text = faq.desc

        val isExpanded = expandedPositionSet.contains(position)
        holder.faqDesc.visibility = if(isExpanded) View.VISIBLE else View.GONE
        holder.seeDetail.setImageResource(if (isExpanded) R.drawable.add_2 else R.drawable.add)

        holder.seeDetail.setOnClickListener {
            if (isExpanded) {
                expandedPositionSet.remove(position)
                holder.faqDesc.visibility = View.GONE
                holder.seeDetail.setImageResource(R.drawable.add)
            } else {
                expandedPositionSet.add(position)
                holder.faqDesc.visibility = View.VISIBLE
                holder.seeDetail.setImageResource(R.drawable.add_2)
            }
            notifyItemChanged(position)
        }
    }
}