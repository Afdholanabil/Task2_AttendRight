package com.example.task2_attendright.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemberModel(
    val profileImage: Int,
    val name: String,
    val email: String,
    var isSelected: Boolean
) : Parcelable
