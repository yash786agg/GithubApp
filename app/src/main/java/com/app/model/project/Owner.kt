package com.app.model.project

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(@field:Json(name = "avatar_url") val avatar : String?) : Parcelable