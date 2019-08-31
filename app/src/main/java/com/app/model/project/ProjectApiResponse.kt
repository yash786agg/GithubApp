package com.app.model.project

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProjectApiResponse(@SerializedName("total_count") val totalCount : Int,
                              @SerializedName("items") val projects : List<Project>) : Parcelable