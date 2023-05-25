package com.example.imgcreater.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class generateData(
    val ImageUrl: String,
    val word: String
): Parcelable