package com.example.task2_attendright.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MeetModel(
    val titleMeet: String? = "",
    val meetingModeMeet: String? = "",
    val meetingViaMeet: String? = "",
    val dateMeet: String? = "",
    val timeMeet: String? = "",
    val descriptionMeet: String? = ""
) : Parcelable