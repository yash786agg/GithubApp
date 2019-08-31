package com.app.model.project

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Project(@SerializedName("id") val projectId : Long?,
                   @SerializedName("name") val name : String?,
                   @SerializedName("private") val private : Boolean?,
                   @SerializedName("description") val description : String?,
                   @SerializedName("updated_at") val updatedAt : String?,
                   @SerializedName("language") val language : String,
                   @SerializedName("stargazers_count") val stars : String?,
                   @SerializedName("html_url") val url : String?,
                   @SerializedName("owner") val owner : Owner?) : Parcelable