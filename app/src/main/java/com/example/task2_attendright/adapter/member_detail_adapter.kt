package com.example.task2_attendright.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.MemberDetailModel
import com.example.task2_attendright.data.local.MemberModel

class MemberDetailAdapter(
    private val memberList: List<MemberDetailModel>,
) :
    RecyclerView.Adapter<MemberDetailAdapter.MemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detail_member_recycler_view, parent, false)
        return MemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val member = memberList[position]
        holder.bind(member)
    }

    override fun getItemCount(): Int = memberList.size

    inner class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivProfile: ImageView = itemView.findViewById(R.id.ivProfile)
        private val tvName: TextView = itemView.findViewById(R.id.txt_name_members)
        private val tvEmail: TextView = itemView.findViewById(R.id.txt_email_members)

        fun bind(member: MemberDetailModel) {
            ivProfile.setImageResource(member.profileImage)
            tvName.text = member.name
            tvEmail.text = member.email
        }
    }
}