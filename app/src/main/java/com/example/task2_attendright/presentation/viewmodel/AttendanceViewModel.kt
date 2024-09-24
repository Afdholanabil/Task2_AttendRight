package com.example.task2_attendright.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AttendanceViewModel : ViewModel() {

        private val _selectedMonth = MutableLiveData<Int>()
        val selectedMonth: LiveData<Int> get() = _selectedMonth

        private val _selectedYear = MutableLiveData<Int>()
        val selectedYear: LiveData<Int> get() = _selectedYear

        fun updateMonth(month: Int) {
            _selectedMonth.value = month
        }

        fun updateYear(year: Int) {
            _selectedYear.value = year
        }
    }
