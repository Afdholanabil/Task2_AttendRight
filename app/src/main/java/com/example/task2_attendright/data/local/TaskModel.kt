package com.example.task2_attendright.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class TaskModel(
    val taskTitleProject: String,
) : Parcelable