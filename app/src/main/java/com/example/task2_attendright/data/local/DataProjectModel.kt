package com.example.task2_attendright.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

data class DataProjectModel(
    var dateProject: String? = null,
    var descriptionProject: String? = null,
    var pointProject: String? = null,
    var timeProject: String? = null,
    var titleProject: String? = null,
    var percentData: String? = null
)