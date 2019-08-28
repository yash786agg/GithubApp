package com.app.model.main

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubApiResponse(@SerializedName("total_count") val totalCount: Int,
                             @SerializedName("items") val projects : List<Projects>) : Parcelable