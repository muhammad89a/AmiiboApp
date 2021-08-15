package com.muhammad89a.amiiboapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReleaseDTO(
    val au: String,
    val eu: String,
    val jp: String,
    val na: String,
): Parcelable
