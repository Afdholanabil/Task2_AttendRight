package com.example.task2_attendright.presentation.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.task2_attendright.presentation.ui.adapter.MeetAdapter
import com.example.task2_attendright.data.local.MeetModel
import com.example.task2_attendright.databinding.DialogDatePickerBinding
import com.example.task2_attendright.databinding.FragmentMeetBinding
import com.example.task2_attendright.presentation.ui.activities.AddMeetActivity
import com.example.task2_attendright.presentation.ui.adapter.CalendarAdapterMeet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MeetFragment : Fragment() {
    private var _binding: FragmentMeetBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseRef: DatabaseReference

    // calendar
    private val lastDayInCalendar = Calendar.getInstance(Locale.ENGLISH)
    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    private val currentDay = currentDate[Calendar.DAY_OF_MONTH]
    private val currentMonth = currentDate[Calendar.MONTH]
    private val currentYear = currentDate[Calendar.YEAR]
    private var selectedDay: Int = currentDay
    private var selectedMonth: Int = currentMonth
    private var selectedYear: Int = currentYear
    private val dates = ArrayList<Date>()
    private val meetListData = mutableListOf<MeetModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeetBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.calendarRecyclerView)
        setUpCalendar()
        lastDayInCalendar.add(Calendar.MONTH, 6)
        binding.rvDataMeet.adapter?.notifyDataSetChanged()
        databaseRef = FirebaseDatabase.getInstance().reference

//        val meetAdapter = MeetAdapter(meetData, this)
//        binding.rvDataMeet.layoutManager = LinearLayoutManager(context)
//        binding.rvDataMeet.adapter = meetAdapter

        binding.btnAddMeet.setOnClickListener {
            val intent = Intent(activity, AddMeetActivity::class.java)
            startActivity(intent)
        }

        binding.btnArrowDatePicker.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val dialogBinding = DialogDatePickerBinding.inflate(LayoutInflater.from(requireContext()))
        val daySpinner: Spinner = dialogBinding.spinnerDay
        val monthSpinner: Spinner = dialogBinding.spinnerMonth
        val yearSpinner: Spinner = dialogBinding.spinnerYear

        val days = (1..31).map { it.toString() }
        val months = listOf(
            "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        )
        val years = (2024..2030).map { it.toString() }

        daySpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, days)
        monthSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, months)
        yearSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setTitle("Pilih Tanggal")
            .setPositiveButton("OK") { dialog, _ ->

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MeetFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun formatDate(dateString: String): String {
        try {
            val inputFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            val outputFormat = SimpleDateFormat("EEE MMM dd", Locale.ENGLISH)
            return date?.let { outputFormat.format(it) } ?: "Invalid Date"
        } catch (e: Exception) {
            e.printStackTrace()
            return "Invalid"
        }
    }


    private fun setUpCalendar(changeMonth: Calendar? = null) {
        binding.txtCurrentMonth.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        var currentPosition = 0
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)

        selectedDay =
            when {
                changeMonth != null -> changeMonth.getActualMinimum(Calendar.DAY_OF_MONTH)
                else -> currentDay
            }
        selectedMonth =
            when {
                changeMonth != null -> changeMonth[Calendar.MONTH]
                else -> currentMonth
            }

        while (dates.size < maxDaysInMonth) {
            if (monthCalendar[Calendar.DAY_OF_MONTH] == selectedDay)
                currentPosition = dates.size
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.calendarRecyclerView.layoutManager = layoutManager
        val calendarAdapterMeet = CalendarAdapterMeet(requireContext(), dates, currentDate, changeMonth)
        binding.calendarRecyclerView.adapter = calendarAdapterMeet

        when {
            currentPosition > 2 -> binding.calendarRecyclerView.scrollToPosition(currentPosition - 3)
            maxDaysInMonth - currentPosition < 2 -> binding.calendarRecyclerView.scrollToPosition(
                currentPosition
            )

            else -> binding.calendarRecyclerView.scrollToPosition(currentPosition)
        }

        calendarAdapterMeet.setOnItemClickListener(object : CalendarAdapterMeet.OnItemClickListener,
            AdapterView.OnItemClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemClick(position: Int) {
                val clickCalendar = Calendar.getInstance()
                clickCalendar.time = dates[position]
                selectedDay = clickCalendar[Calendar.DAY_OF_MONTH]
                selectedMonth = clickCalendar[Calendar.MONTH] + 1
                selectedYear = clickCalendar[Calendar.YEAR]
                val selectedDateRaw = String.format("%02d/%02d/%02d", selectedDay, selectedMonth, selectedYear % 100)
                val selectedDate = formatDate(selectedDateRaw)
                displayDataForSelectedDate(selectedDate)
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                // Not yet implemented
            }
        })
    }

    private fun displayDataForSelectedDate(selectedDate: String) {
        databaseRef.child("meets").addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                meetListData.clear()
                val meetData = mutableListOf<MeetModel>()
                if (!snapshot.exists() || snapshot.childrenCount == 0L) {
                    binding.imgNoData.visibility = View.VISIBLE
                    binding.rvDataMeet.visibility = View.GONE
                    Log.e("Firebase Gagal", "Data not founddd")
                } else {
                    for (projectSnapshot in snapshot.children) {
                        val meet = projectSnapshot.getValue(MeetModel::class.java)
                        meet?.let {
                            val formattedDate = formatDate(it.dateMeet!!)
                            if (formattedDate == selectedDate) {
                                meetData.add(it)
                            }
                        }
                    }
                    if (meetData.isNotEmpty()) {
                        binding.rvDataMeet.setHasFixedSize(true)
                        binding.rvDataMeet.layoutManager = LinearLayoutManager(context)
                        binding.rvDataMeet.adapter = MeetAdapter(requireContext(), meetData)
                        binding.imgNoData.visibility = View.GONE
                        binding.rvDataMeet.visibility = View.VISIBLE
                    } else {
                        binding.imgNoData.visibility = View.VISIBLE
                        binding.rvDataMeet.visibility = View.GONE
                        Log.e("Firebase Gagal", "No data found for selected date")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase Error", "Error ${error.message}")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    val meetData = listOf(
//        MeetModel(
//            "Meeting for World War 3",
//            "Online",
//            "meet.google.com/eer-iuyi-opk",
//            "15:20 pm"
//        ),
//        MeetModel(
//            "Meeting for World War 3",
//            "Offline",
//            "Ruang Training Graha Pena, Surabaya, Jawa Timur.",
//            "10:20 am"
//        )
//    )
}
