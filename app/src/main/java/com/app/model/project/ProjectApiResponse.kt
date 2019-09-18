package com.app.model.project

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProjectApiResponse(@field:Json(name = "total_count") val totalCount : Int,
                              @field:Json(name = "items") val projects : List<Project>) : Parcelable