package com.app.ui.project.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.api.NetworkState
import com.app.extensions.ProjectItem
import com.app.model.project.Project
import com.app.nandroid.R
import com.app.ui.project.details.ProjectDetailsActivity
import com.app.ui.project.list.adapter.ProjectListAdapter
import com.app.utils.Constants.Companion.noData
import com.app.utils.Constants.Companion.projectTag
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)

        /*
         * Initialize the ViewModel
         * */

        projectListViewModel = ViewModelProviders.of(this,providerFactory).get(ProjectListViewModel::class.java)

        initRecyclerView()
        if(uiHelper.getConnectivityStatus())
            subscribeObservers()
        else
            uiHelper.showSnackBar(main_rootView, resources.getString(R.string.error_message_network))
    }

    private fun subscribeObservers() {

        /*
         * When a new page is available, we call submitList() method
         * of the PagedListAdapter class
         * */

        projectListViewModel.projects.observe(this, Observer {
            if(it != null)
                projectListAdapter.submitList(it)
        })

        /*
         * Progress Updater
         * */
        projectListViewModel.networkState!!.observe(this, Observer {

            it?.let {
                when(it) {
                     is NetworkState.Loading -> showProgressBar(true)
                     is NetworkState.Success -> showProgressBar(false)
                     is NetworkState.Error -> {
                        showProgressBar(false)
                        if(it.errorCode == noData)
                            uiHelper.showSnackBar(main_rootView, resources.getString(R.string.error_no_data))
                        else
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

    override fun onProjectItemClickListener(project: Project?) {

        if(project != null) {
            val intent = Intent(this, ProjectDetailsActivity::class.java)
            intent.putExtra(projectTag,project)
            startActivity(intent)
        }
    }
}
