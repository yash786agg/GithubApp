package com.app.ui.project.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.api.NetworkState
import com.app.extensions.ProjectItem
import com.app.model.project.Project
import com.app.githubapp.R
import com.app.ui.project.details.ProjectDetailsActivity
import com.app.ui.project.list.adapter.ProjectListAdapter
import com.app.utils.Constants.Companion.NO_DATA
import com.app.utils.Constants.Companion.EXTRA_PROJECT
import com.app.utils.Constants.Companion.NO_MORE_DATA
import com.app.utils.UiHelper
import kotlinx.android.synthetic.main.activity_project_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProjectListActivity : AppCompatActivity() , ProjectItem {

    // FOR DATA ---
    private val projectListViewModel : ProjectListViewModel by viewModel()
    private val uiHelper : UiHelper by inject()
    private val projectListAdapter = ProjectListAdapter()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)

        initRecyclerView()

        /*
         * Check Internet Connection
         * */

        if(uiHelper.getConnectivityStatus()) configureObservables()
        else uiHelper.showSnackBar(main_rootView, resources.getString(R.string.error_message_network))
    }

    private fun configureObservables() {

        /*
         * When a new page is available, we call submitList() method
         * of the PagedListAdapter class
         * */

        projectListViewModel.projects.observe(this, Observer {

            it?.let { projectListAdapter.submitList(it) }
        })

        /*
         * Progress Updater
         * */
        projectListViewModel.networkState?.observe(this, Observer {

            it?.let {
                when(it) {
                     is NetworkState.Loading -> showProgressBar(true)
                     is NetworkState.Success -> showProgressBar(false)
                     is NetworkState.Error -> {
                        showProgressBar(false)
                        if(it.errorCode == NO_DATA)
                            uiHelper.showSnackBar(main_rootView, resources.getString(R.string.error_no_data))
                        else if(it.errorCode == NO_MORE_DATA)
                            uiHelper.showSnackBar(main_rootView, resources.getString(R.string.error_no_more_data))
                        else uiHelper.showSnackBar(main_rootView, resources.getString(R.string.error_message))
                    }
                }
            }
        })
    }

    private fun initRecyclerView() {
        /*
         * Setup the adapter class for the RecyclerView
         * */
        recylv_project.layoutManager = LinearLayoutManager(this)
        recylv_project.adapter = projectListAdapter
        projectListAdapter.setonProjectItemClickListener(this)
    }

    // UPDATE UI ----
    private fun showProgressBar(display : Boolean) {
        if(!display) progress_bar.visibility = View.GONE
        else progress_bar.visibility = View.VISIBLE
    }

    override fun onProjectItemClickListener(project : Project?) {

        project?.let {
            val intent = Intent(this, ProjectDetailsActivity::class.java)
            intent.putExtra(EXTRA_PROJECT,project)
            startActivity(intent)
        }
    }
}
