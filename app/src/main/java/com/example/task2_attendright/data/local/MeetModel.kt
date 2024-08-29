package com.example.task2_attendright.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Suppress("PARCELABLE_PRIMARY_CONSTRUCTOR_IS_EMPTY")
@Parcelize
class MeetModel(
    val titleMeet: String,
    val meetingModeMeet: String,
    val linkMeet: String,
    val timeMeet: String,
) : Parcelable