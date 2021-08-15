package com.muhammad89a.amiiboapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AmiiboContainerDTO(
    val amiibo: ArrayList<AmiiboDTO>
) : Parcelable