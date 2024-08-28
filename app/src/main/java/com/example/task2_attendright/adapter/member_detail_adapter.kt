package com.example.task2_attendright.adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.MemberDetailModel

class MemberDetailAdapter(
    private val context: Context,
    private val memberList: List<MemberDetailModel>,
) : RecyclerView.Adapter<MemberDetailAdapter.MemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detail_member_recycler_view, parent, false)
        return MemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val member = memberList[position]
        holder.itemView.setOnClickListener {
            showUserProfileDialog(member)
        }
        holder.bind(member)
    }

    override fun getItemCount(): Int = memberList.size

    private fun showUserProfileDialog(member: MemberDetailModel) {
        val dialog = Dialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.member_pop_up, null)
        dialog.setContentView(view)
        val userNameTextView = view.findViewById<TextView>(R.id.userNameTextView)
        val userEmailTextView = view.findViewById<TextView>(R.id.userEmailTextView)
        val userImageView = view.findViewById<ImageView>(R.id.userImageView)

        userNameTextView.text = member.name
        userEmailTextView.text = member.email
        userImageView.setImageResource(member.profileImage)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

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
