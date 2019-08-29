package com.app.model.project

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(@SerializedName("avatar_url") val avatar : String) : Parcelable