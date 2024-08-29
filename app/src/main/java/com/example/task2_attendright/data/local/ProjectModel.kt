package com.example.task2_attendright.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProjectModel (
    val dateProject: String,
    val titleProject: String,
) : Parcelable