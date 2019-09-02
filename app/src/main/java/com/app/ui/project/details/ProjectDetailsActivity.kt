package com.app.ui.project.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import com.app.nandroid.R
import com.app.utils.Constants.Companion.EXTRA_PROJECT
import dagger.android.support.DaggerAppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.nandroid.databinding.ActivityProjectDetailsBinding

class ProjectDetailsActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityProjectDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_project_details)

        if(intent != null && intent.hasExtra(EXTRA_PROJECT)) {
            binding.project = intent.getParcelableExtra(EXTRA_PROJECT)
            binding.callback = this
        }
    }

    fun onVisitRepoClick(webPageUrl : String) {

        if(!TextUtils.isEmpty(webPageUrl)) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(webPageUrl)
            startActivity(intent)
        }
    }
}