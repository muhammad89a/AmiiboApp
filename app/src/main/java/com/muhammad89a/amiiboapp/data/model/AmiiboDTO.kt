package com.muhammad89a.amiiboapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Amiibos",primaryKeys = ["head","tail"])
data class AmiiboDTO(
    @SerializedName("amiiboSeries")
    val amiiboSeries: String?,
    @SerializedName("character")
    val character: String?,
    @SerializedName("gameSeries")
    val gameSeries: String?,
    @SerializedName("head")
    val head: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("tail")
    val tail: String,
    @SerializedName("type")
    val type: String?,
    @Transient
    @ColumnInfo(name = "purchased")
    var purchased: Boolean,
//    @SerializedName("release")
//    val release: ReleaseDTO,

): Parcelable
