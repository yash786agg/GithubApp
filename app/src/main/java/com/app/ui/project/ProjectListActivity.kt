package com.app.ui.project

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.api.NetworkState
import com.app.extensions.ProjectItem
import com.app.model.project.Project
import com.app.nandroid.R
import com.app.ui.project.adapter.ProjectListAdapter
import com.app.utils.UiHelper
import com.app.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_project_list.*
import javax.inject.Inject

class ProjectListActivity : DaggerAppCompatActivity() , ProjectItem {

    // FOR DATA ---
    @Inject lateinit var uiHelper: UiHelper
    @Inject lateinit var providerFactory: ViewModelProviderFactory
    private lateinit var projectListViewModel : ProjectListViewModel
    private val projectListAdapter = ProjectListAdapter()

    private val TAG : String = "ProjectListActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)

        /*
         * Initialize the ViewModel
         * */

        projectListViewModel = ViewModelProviders.of(this,providerFactory).get(ProjectListViewModel::class.java)

        initRecyclerView()

        subscribeObservers()
    }

    private fun subscribeObservers() {

        /*
         * When a new page is available, we call submitList() method
         * of the PagedListAdapter class
         * */

        projectListViewModel.projects.observe(this, Observer {
            if(it != null)
            {
                //Log.e(TAG, "ProjectListActivity projects if "+it[0]!!.fullName)
                projectListAdapter.submitList(it)
            }
        })

        /*
         * Progress Updater
         * */
        projectListViewModel.networkState!!.observe(this, Observer {

            if(it != null)
            {
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

    private fun initRecyclerView() {
        /*
         * Setup the adapter class for the RecyclerView
         * */
        project_recylv.layoutManager = LinearLayoutManager(this)
        project_recylv.adapter = projectListAdapter
        projectListAdapter.setonProjectItemClickListener(this)
    }

    // UPDATE UI ----
    private fun showProgressBar(display : Boolean) {
        if(!display)
            progress_bar.visibility = View.GONE
        else
            progress_bar.visibility = View.VISIBLE
    }

    override fun onProjectItemClickListener(project: Project) {
        Log.e(TAG, "ProjectListActivity onProjectItemClickListener project ${project.name}")
    }
}
