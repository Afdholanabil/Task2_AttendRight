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
import com.example.task2_attendright.data.local.DataProjectModel
import com.example.task2_attendright.databinding.DialogDatePickerBinding
import com.example.task2_attendright.databinding.FragmentProjectBinding
import com.example.task2_attendright.presentation.ui.activities.AddProjectActivity
import com.example.task2_attendright.presentation.ui.adapter.CalendarAdapterMeet
import com.example.task2_attendright.presentation.ui.adapter.CalendarAdapterProject
import com.example.task2_attendright.presentation.ui.adapter.ProjectAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ProjectFragment : Fragment() {
    private var _binding: FragmentProjectBinding? = null
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
    private val projectListData = mutableListOf<DataProjectModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.calendarRecyclerView)
        setUpCalendar()
        lastDayInCalendar.add(Calendar.MONTH, 6)
        binding.rvDataProject.adapter?.notifyDataSetChanged()
        databaseRef = FirebaseDatabase.getInstance().reference

        binding.btnArrowDatePicker.setOnClickListener {
            showDatePickerDialog()
        }
        binding.btnAddProject.setOnClickListener {
            val intent = Intent(activity, AddProjectActivity::class.java)
            startActivity(intent)
        }
    }

    private fun formatDate(dateString: String): String {
        try {
            val inputFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault()) // format asli dari Firebase
            val date = inputFormat.parse(dateString)
            val outputFormat = SimpleDateFormat("EEE MMM dd", Locale.ENGLISH) // format baru yang ingin digunakan
            return date?.let { outputFormat.format(it) } ?: "Invalid Date"
        } catch (e: Exception) {
            e.printStackTrace()
            return "Invalid"
        }
    }


    // for display data from recycler view
    private fun displayData() {
        databaseRef.child("projects").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                projectListData.clear()
                val projectData = mutableListOf<DataProjectModel>()
                if (!snapshot.exists() || snapshot.childrenCount == 0L) {
                    binding.imgNoData.visibility = View.VISIBLE
                    binding.rvDataProject.visibility = View.GONE
                    Log.e("Firebase Gagal", "Data not founddd")
                } else {
                    for (projectSnapshot in snapshot.children) {
                        val project = projectSnapshot.getValue(DataProjectModel::class.java)
                        project?.let {
                            projectData.add(it)
                        }
                    }
                    if (projectData.isNotEmpty()) {
                        binding.rvDataProject.setHasFixedSize(true)
                        binding.rvDataProject.layoutManager = LinearLayoutManager(context)
                        binding.rvDataProject.adapter =
                            ProjectAdapter(requireContext(), projectData)
                        binding.imgNoData.visibility = View.GONE
                        binding.rvDataProject.visibility = View.VISIBLE
                    } else {
                        binding.imgNoData.visibility = View.VISIBLE
                        binding.rvDataProject.visibility = View.GONE
                        Log.e("Firebase Gagal", "Data not founddd")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase Error", "Error ${error.message}")
            }
        })
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
//                val selectedDay = daySpinner.selectedItem.toString()
//                val selectedMonth = monthSpinner.selectedItem.toString()
//                val selectedYear = yearSpinner.selectedItem.toString()
//
//                this.selectedDay = selectedDay.toInt()
//                this.selectedMonth = selectedMonth.toInt() - 1
//
//                cal.set(Calendar.DAY_OF_MONTH, this.selectedDay)
//                cal.set(Calendar.MONTH, this.selectedMonth)
//                setUpCalendar(cal)
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
            ProjectFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
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
        val calendarAdapterMeet = CalendarAdapterProject(requireContext(), dates, currentDate, changeMonth)
        binding.calendarRecyclerView.adapter = calendarAdapterMeet

        when {
            currentPosition > 2 -> binding.calendarRecyclerView.scrollToPosition(currentPosition - 3)
            maxDaysInMonth - currentPosition < 2 -> binding.calendarRecyclerView.scrollToPosition(
                currentPosition
            )

            else -> binding.calendarRecyclerView.scrollToPosition(currentPosition)
        }

        // display when calendar clicked
        calendarAdapterMeet.setOnItemClickListener(object : CalendarAdapterProject.OnItemClickListener,
            AdapterView.OnItemClickListener {
            @SuppressLint("NotifyDataSetChanged", "DefaultLocale")
            override fun onItemClick(position: Int) {
                val clickCalendar = Calendar.getInstance()
                clickCalendar.time = dates[position]
                selectedDay = clickCalendar[Calendar.DAY_OF_MONTH]
                selectedMonth = clickCalendar[Calendar.MONTH] + 1
                selectedYear = clickCalendar[Calendar.YEAR]
                val selectedDateRaw = String.format("%02d/%02d/%02d", selectedDay, selectedMonth, selectedYear % 100)
                val selectedDate = formatDate(selectedDateRaw)
//                Log.d("Click Test", "posisi = $position, tgl = $selectedDateRaw")
//                Log.d("SelectedDate", "date select $selectedDate")
                displayDataForSelectedDate(selectedDate)
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("CalendarClick", "Item kalender diklik: Posisi = $p0")
            }
        })
    }

    private fun displayDataForSelectedDate(selectedDate: String) {
        databaseRef.child("projects").addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                projectListData.clear()
                val projectData = mutableListOf<DataProjectModel>()
                if (!snapshot.exists() || snapshot.childrenCount == 0L) {
                    binding.imgNoData.visibility = View.VISIBLE
                    binding.rvDataProject.visibility = View.GONE
                    Log.e("Firebase Gagal", "Data not founddd")
                } else {
                    for (projectSnapshot in snapshot.children) {
                        val project = projectSnapshot.getValue(DataProjectModel::class.java)
                        project?.let {
                            val formattedDate = formatDate(it.dateProject!!)
                            if (formattedDate == selectedDate) {
                                projectData.add(it)
                            }
                        }
                    }
                    if (projectData.isNotEmpty()) {
                        binding.rvDataProject.setHasFixedSize(true)
                        binding.rvDataProject.layoutManager = LinearLayoutManager(context)
                        binding.rvDataProject.adapter = ProjectAdapter(requireContext(), projectData)
                        binding.imgNoData.visibility = View.GONE
                        binding.rvDataProject.visibility = View.VISIBLE
                    } else {
                        binding.imgNoData.visibility = View.VISIBLE
                        binding.rvDataProject.visibility = View.GONE
                        Log.e("Firebase Gagal", "No data found for selected date")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase Error", "Error ${error.message}")
            }
        })
    }



//    val projectData = listOf(
//        ProjectModel(
//            "Monday, 12 November 2024",
//            "Project Web Design",
//            "Project Web Design",
//            "Project Web Design",
//            25,
//            25
//        ),
//        ProjectModel("Saturday, 20 November 2024", "Project Web Design", 55),
//        ProjectModel("Monday, 12 November 2024", "Project Web Design", 65),
//        ProjectModel("Monday, 12 November 2024", "Project Web Design", 75)
//    )
}