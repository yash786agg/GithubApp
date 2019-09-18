package com.app.model.project

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Project(@field:Json(name = "id") val projectId : Long?,
                   @field:Json(name = "name") val name : String?,
                   @field:Json(name = "private") val private : Boolean?,
                   @field:Json(name = "description") val description : String?,
                   @field:Json(name = "updated_at") val updatedAt : String?,
                   @field:Json(name = "language") val language : String,
                   @field:Json(name = "stargazers_count") val stars : Int?,
                   @field:Json(name = "html_url") val url : String?,
                   @field:Json(name = "owner") val owner : Owner?) : Parcelable