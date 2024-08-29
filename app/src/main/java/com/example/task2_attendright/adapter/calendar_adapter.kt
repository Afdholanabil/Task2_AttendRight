package com.example.task2_attendright.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_attendright.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarAdapter(
    private val context: Context,
    private val data: ArrayList<Date>,
    private val currentDate: Calendar,
    private val changeMonth: Calendar?
) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    private var mListener: AdapterView.OnItemClickListener? = null
    private var index = -1
    private var selectCurrentDate = true
    private val currentMonth = currentDate[Calendar.MONTH]
    private val currentYear = currentDate[Calendar.YEAR]
    private val currentDay = currentDate[Calendar.DAY_OF_MONTH]
    private val selectedDay =
        when {
            changeMonth != null -> changeMonth.getActualMinimum(Calendar.DAY_OF_MONTH)
            else -> currentDay
        }
    private val selectedMonth =
        when {
            changeMonth != null -> changeMonth[Calendar.MONTH]
            else -> currentMonth
        }
    private val selectedYear =
        when {
            changeMonth != null -> changeMonth[Calendar.YEAR]
            else -> currentYear
        }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_calendar_day_meet, parent, false)
        return ViewHolder(view, mListener!!)
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss", Locale.ENGLISH)
        val cal = Calendar.getInstance()
        cal.time = data[position]

        val displayMonth = cal[Calendar.MONTH]
        val displayYear = cal[Calendar.YEAR]
        val displayDay = cal[Calendar.DAY_OF_MONTH]

        try {
            val dayInWeek = sdf.parse(cal.time.toString())!!
            sdf.applyPattern("EEE")
            holder.txtDayInWeek.text = sdf.format(dayInWeek).toString()
        } catch (ex: Exception) {
            Log.v("Exception", ex.localizedMessage!!)
        }
        holder.txtDay.text = cal[Calendar.DAY_OF_MONTH].toString()

        if (displayYear >= currentYear)
            if (displayMonth >= currentMonth || displayYear > currentYear)
                if (displayDay >= currentDay || displayMonth > currentMonth || displayYear > currentYear) {
                    holder.linearLayout!!.setOnClickListener {
                        index = position
                        selectCurrentDate = false
                        notifyDataSetChanged()
                    }

                    if (index == position)
                        makeItemSelected(holder)
                    else {
                        if (displayDay == selectedDay
                            && displayMonth == selectedMonth
                            && displayYear == selectedYear
                            && selectCurrentDate
                        )
                            makeItemSelected(holder)
                        else
                            makeItemDefault(holder)
                    }
                } else makeItemDisabled(holder)
            else makeItemDisabled(holder)
        else makeItemDisabled(holder)

    }

    inner class ViewHolder(itemView: View, val listener: AdapterView.OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        var txtDay: TextView = itemView.findViewById(R.id.txt_date)
        var txtDayInWeek: TextView = itemView.findViewById(R.id.txt_day)
        var linearLayout: View = itemView.findViewById(R.id.calendar_linear_layout)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(null, it, adapterPosition, it.id.toLong())
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: AdapterView.OnItemClickListener) {
        mListener = listener
    }

    private fun makeItemDisabled(holder: ViewHolder) {
        holder.txtDay!!.setTextColor(ContextCompat.getColor(context, R.color.white))
        holder.txtDayInWeek!!.setTextColor(ContextCompat.getColor(context, R.color.white))
        holder.linearLayout!!.setBackgroundResource(R.drawable.rounded_card_calendar_meet_unselected)
        holder.linearLayout!!.isEnabled = false
    }

    private fun makeItemSelected(holder: ViewHolder) {
        holder.txtDay!!.setTextColor(ContextCompat.getColor(context, R.color.p1))
        holder.txtDayInWeek!!.setTextColor(ContextCompat.getColor(context, R.color.p1))
        holder.linearLayout!!.setBackgroundResource(R.drawable.rounded_card_calendar_meet_selected)
        holder.linearLayout!!.isEnabled = false
    }

    private fun makeItemDefault(holder: ViewHolder) {
        holder.txtDay!!.setTextColor(ContextCompat.getColor(context, R.color.white))
        holder.txtDayInWeek!!.setTextColor(ContextCompat.getColor(context, R.color.white))
        holder.linearLayout!!.setBackgroundResource(R.drawable.rounded_card_calendar_meet_unselected)
        holder.linearLayout!!.isEnabled = true
    }
}
