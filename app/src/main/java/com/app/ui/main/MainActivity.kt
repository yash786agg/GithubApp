package com.app.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.api.NetworkState
import com.app.nandroid.R
import com.app.utils.UiHelper
import com.app.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    // FOR DATA ---
    @Inject lateinit var uiHelper: UiHelper
    @Inject lateinit var providerFactory: ViewModelProviderFactory
    private lateinit var mainViewModel : MainViewModel

    private val TAG : String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * Initialize the ViewModel
         * */

        mainViewModel = ViewModelProviders.of(this,providerFactory).get(MainViewModel::class.java)

        subscribeObservers()
    }

    private fun subscribeObservers() {

        /*
         * When a new page is available, we call submitList() method
         * of the PagedListAdapter class
         * */

        mainViewModel.projects.observe(this, Observer {
            if(it != null)
                Log.e(TAG, "MainActivity projects if ")
        })

        /*
         * Progress Updater
         * */
        mainViewModel.networkState!!.observe(this, Observer {

            if(it != null)
            {
                Log.e(TAG, "MainActivity networkState if "+it)

                when(it) {
                    NetworkState.LOADING -> showProgressBar(true)
                    NetworkState.SUCCESS -> showProgressBar(false)
                    NetworkState.ERROR -> {
                        showProgressBar(false)

                        uiHelper.showSnackBar(main_rootView, resources.getString(R.string.error_message))
                    }
                }
            }
        })
    }

    // UPDATE UI ----
    private fun showProgressBar(display : Boolean)
    {
        if(!display)
            progress_bar.visibility = View.GONE
        else
            progress_bar.visibility = View.VISIBLE
    }
}
