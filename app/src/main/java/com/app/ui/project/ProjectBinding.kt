package com.app.ui.project

import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.app.nandroid.R
import com.app.utils.UiHelper
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest

class ProjectBinding(private val fresco: PipelineDraweeControllerBuilder,
                     private val uiHelper: UiHelper) {

    @BindingAdapter("updatedAt")
    fun repoUpdateText(textView : TextView, updatedAt : String?) {

        if(!TextUtils.isEmpty(updatedAt)) {

            val formattedTime = updatedAt?.let { uiHelper.getProjectUpdatedTime(it) }

            if(!TextUtils.isEmpty(formattedTime) && formattedTime != "") {
                val updatedTime = textView.context.resources.getString(R.string.updated)+" "+ formattedTime
                textView.text = updatedTime
            }
            else
                textView.visibility = View.GONE
        }
    }

    @BindingAdapter("avatarUrl")
    fun loadImage(view: SimpleDraweeView, imageUrl: String?) {

        if(!TextUtils.isEmpty(imageUrl)) {
            val draweeController = fresco.setImageRequest(ImageRequest.fromUri(Uri.parse(imageUrl)))
                .setOldController(view.controller).build()
            view.controller = draweeController
        }
    }
}