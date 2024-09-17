package com.example.task2_attendright.presentation.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.DataProjectModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarAdapterProject(
    private val context: Context,
    private val data: ArrayList<Date>,
    private val currentDate: Calendar,
    private val changeMonth: Calendar?
) : RecyclerView.Adapter<CalendarAdapterProject.ViewHolder>() {

    private var mListener: OnItemClickListener? = null
    private var selectedPosition = RecyclerView.NO_POSITION
    private val currentMonth = currentDate[Calendar.MONTH]
    private val currentYear = currentDate[Calendar.YEAR]
    private val currentDay = currentDate[Calendar.DAY_OF_MONTH]
    private lateinit var databaseRef: DatabaseReference
    private val meetListData = mutableListOf<DataProjectModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_calendar_day_meet, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    private fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            val outputFormat = SimpleDateFormat("EEE MMM dd", Locale.ENGLISH)
            date?.let { outputFormat.format(it) } ?: "Invalid Date"
        } catch (e: Exception) {
            e.printStackTrace()
            "Invalid"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val sdf = SimpleDateFormat("EEE MMM dd", Locale.ENGLISH)
        val cal = Calendar.getInstance()
        cal.time = data[position]
        val displayMonth = cal[Calendar.MONTH]
        val displayYear = cal[Calendar.YEAR]
        val displayDay = cal[Calendar.DAY_OF_MONTH]
        val itemDateRaw = String.format("%02d/%02d/%02d", displayDay, displayMonth + 1, displayYear % 100)
        val itemDate = formatDate(itemDateRaw)

        makeItemDefault(holder)
        if (position == selectedPosition) {
            makeItemSelected(holder)
        }

        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousPosition)
            notifyItemChanged(position)
            mListener?.onItemClick(position)
        }

        databaseRef = FirebaseDatabase.getInstance().reference
        databaseRef.child("projects").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                meetListData.clear()
                val meetData = mutableListOf<DataProjectModel>()
                if (snapshot.exists() && snapshot.childrenCount > 0) {
                    for (projectSnapshot in snapshot.children) {
                        val meet = projectSnapshot.getValue(DataProjectModel::class.java)
                        meet?.let {
                            val formattedDate = formatDate(it.dateProject!!)
                            if (formattedDate == itemDate) {
                                meetData.add(it)
                            }
                        }
                    }
                    if (meetData.isNotEmpty()) {
                        makeItemHasData(holder)
                    } else {
                        handleItemState(holder, displayDay, displayMonth, displayYear, position)
                    }
                } else {
                    handleItemState(holder, displayDay, displayMonth, displayYear, position)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error fetching data: ${error.message}")
            }
        })

        try {
            val dayInWeek = cal.time
            sdf.applyPattern("EEE")
            holder.txtDayInWeek.text = sdf.format(dayInWeek)
        } catch (ex: Exception) {
            Log.v("Exception", ex.localizedMessage!!)
        }

        holder.txtDay.text = displayDay.toString()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtDay: TextView = itemView.findViewById(R.id.txt_date)
        var txtDayInWeek: TextView = itemView.findViewById(R.id.txt_day)
        var linearLayout: View = itemView.findViewById(R.id.calendar_linear_layout)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    private fun handleItemState(
        holder: ViewHolder,
        displayDay: Int,
        displayMonth: Int,
        displayYear: Int,
        position: Int
    ) {
        if (displayYear >= currentYear) {
            if (displayMonth >= currentMonth || displayYear > currentYear) {
                if (displayDay >= currentDay || displayMonth > currentMonth || displayYear > currentYear) {
                    if (selectedPosition == position) {
                        makeItemSelected(holder)
                    } else {
                        makeItemDefault(holder)
                    }
                } else {
                    makeItemDisabled(holder)
                }
            } else {
                makeItemDisabled(holder)
            }
        } else {
            makeItemDisabled(holder)
        }
    }

    private fun makeItemDisabled(holder: ViewHolder) {
        holder.txtDay.setTextColor(ContextCompat.getColor(context, R.color.white))
        holder.txtDayInWeek.setTextColor(ContextCompat.getColor(context, R.color.white))
        holder.linearLayout.setBackgroundResource(R.drawable.rounded_card_calendar_meet_unselected)
        holder.linearLayout.isEnabled = false
    }

    private fun makeItemSelected(holder: ViewHolder) {
        holder.txtDay.setTextColor(ContextCompat.getColor(context, R.color.p1))
        holder.txtDayInWeek.setTextColor(ContextCompat.getColor(context, R.color.p1))
        holder.linearLayout.setBackgroundResource(R.drawable.rounded_card_calendar_meet_selected)
        holder.linearLayout.isEnabled = false
    }

    private fun makeItemDefault(holder: ViewHolder) {
        holder.txtDay.setTextColor(ContextCompat.getColor(context, R.color.white))
        holder.txtDayInWeek.setTextColor(ContextCompat.getColor(context, R.color.white))
        holder.linearLayout.setBackgroundResource(R.drawable.rounded_card_calendar_meet_unselected)
        holder.linearLayout.isEnabled = true
    }

    private fun makeItemHasData(holder: ViewHolder) {
        holder.txtDay.setTextColor(ContextCompat.getColor(context, R.color.white))
        holder.txtDayInWeek.setTextColor(ContextCompat.getColor(context, R.color.white))
        holder.linearLayout.setBackgroundResource(R.drawable.rounded_card_calendar_meet_has_data)
        holder.linearLayout.isEnabled = true
    }
}
