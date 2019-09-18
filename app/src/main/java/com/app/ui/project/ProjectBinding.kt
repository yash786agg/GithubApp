package com.app.ui.project

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.request.CachePolicy
import com.app.githubapp.R
import com.app.utils.UiHelper

@BindingAdapter(value = ["avatarUrl"])
fun loadImage(view: ImageView, imageUrl: String?){

    if(!TextUtils.isEmpty(imageUrl))
        view.load(imageUrl) {
            crossfade(true)
            placeholder(R.drawable.place_holder)
            memoryCachePolicy(CachePolicy.DISABLED)
        }
}

@BindingAdapter(value = ["updatedAt"])
fun repoUpdateText(textView : TextView, updatedAt : String?) {
    if(!TextUtils.isEmpty(updatedAt)) {

        val uiHelper = UiHelper(textView.context)
        val formattedTime = updatedAt?.let { uiHelper.getProjectUpdatedTime(it) }

        if(!TextUtils.isEmpty(formattedTime) && formattedTime != "") {
            val updatedTime = textView.context.resources.getString(R.string.updated)+" "+ formattedTime
            textView.text = updatedTime
        }
        else textView.visibility = View.GONE
    }
}