package com.example.task2_attendright.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemberDetailModel(
    val profileImage: Int,
    val name: String,
    val email: String
) : Parcelable