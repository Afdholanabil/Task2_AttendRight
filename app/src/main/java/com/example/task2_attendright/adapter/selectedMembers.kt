package com.example.task2_attendright.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.MemberModel

class SelectedMembersAdapter(
    private val selectedMembers: List<MemberModel>
) : RecyclerView.Adapter<SelectedMembersAdapter.SelectedMemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedMemberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_selected_member_recycler_view, parent, false)
        return SelectedMemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectedMemberViewHolder, position: Int) {
        val member = selectedMembers[position]
        holder.bind(member)
    }

    override fun getItemCount(): Int = selectedMembers.size

    inner class SelectedMemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profileImage: ImageView = itemView.findViewById(R.id.profileImage)

        fun bind(member: MemberModel) {
            profileImage.setImageResource(member.profileImage)
        }
    }
}

