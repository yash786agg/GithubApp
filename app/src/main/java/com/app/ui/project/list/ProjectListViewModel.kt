package com.app.ui.project.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.pagination.datasource.ProjectDataSourceFactory
import com.app.repository.ProjectRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import javax.inject.Inject
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.app.nandroid.api.NetworkState

class ProjectListViewModel @Inject constructor(projectRepository : ProjectRepository) : ViewModel() {

    /**
     * This is a scope for co-routines launched by [ProjectListViewModel]
     * that will be dispatched in a Pool of Thread
     */
    private val ioScope = CoroutineScope(Dispatchers.IO)

    // FOR DATA ---
    private val projectsDataSource = ProjectDataSourceFactory(projectRepository,ioScope)

    // OBSERVABLES ---
    val projects = LivePagedListBuilder(projectsDataSource, pagedListConfig()).build()
    val networkState : LiveData<NetworkState<Int>>? = switchMap(projectsDataSource.dataSource) { it.getNetworkState() }

    // UTILS ---
    private fun pagedListConfig() = PagedList.Config.Builder()
        .setPageSize(20)
        .build()

    /**
     * Cancel co-routines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        ioScope.coroutineContext.cancel()
    }
}