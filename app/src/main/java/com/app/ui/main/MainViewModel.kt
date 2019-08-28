package com.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.pagination.datasource.ProjectsDataSourceFactory
import com.app.repository.ProjectsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import javax.inject.Inject
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.app.api.NetworkState

class MainViewModel @Inject constructor(projectsRepository : ProjectsRepository) : ViewModel() {

    /**
     * This is a scope for co-routines launched by [MainViewModel]
     * that will be dispatched in a Pool of Thread
     */
    private val ioScope = CoroutineScope(Dispatchers.IO)

    // FOR DATA ---
    private val projectsDataSource = ProjectsDataSourceFactory(projectsRepository,ioScope)

    // OBSERVABLES ---
    val projects = LivePagedListBuilder(projectsDataSource, pagedListConfig()).build()
    val networkState: LiveData<NetworkState>? = switchMap(projectsDataSource.dataSource) { it.getNetworkState() }

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