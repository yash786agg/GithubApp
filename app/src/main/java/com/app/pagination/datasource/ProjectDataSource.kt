package com.app.pagination.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.app.api.NetworkState
import com.app.model.project.Project
import com.app.repository.ProjectRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProjectDataSource(private val projectRepository : ProjectRepository,
                        private val scope: CoroutineScope) : PageKeyedDataSource<Int, Project>()
{
    // FOR DATA ---
    private val networkState = MutableLiveData<NetworkState<Int>>()

    override fun loadInitial(params : LoadInitialParams<Int>, callback : LoadInitialCallback<Int, Project>) {

        networkState.postValue(NetworkState.Loading())

        scope.launch {
            try
            {
                val response = projectRepository.getGithubProjects()

                if(response.isSuccessful) {

                    response.body()?.let {

                        val items = response.body()?.projects
                        items?.let { callback.onResult(items, null, null) }
                    }

                    networkState.postValue(NetworkState.Success())
                }
                else networkState.postValue(NetworkState.Error(response.code()))
            }
            catch (exception : HttpException) {
                networkState.postValue(NetworkState.Error(exception.code()))
            }
        }
    }

    override fun loadAfter(params : LoadParams<Int>, callback : LoadCallback<Int, Project>) {
    }

    override fun loadBefore(params : LoadParams<Int>, callback : LoadCallback<Int, Project>) {
    }

    // PUBLIC API ---
    fun getNetworkState() : LiveData<NetworkState<Int>> = networkState
}