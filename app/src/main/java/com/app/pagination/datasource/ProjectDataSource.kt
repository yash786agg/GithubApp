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

        executeQuery(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params : LoadParams<Int>, callback : LoadCallback<Int, Project>) {
        executeQuery(params.key, params.requestedLoadSize) {
            callback.onResult(it, params.key + 1)
        }
    }

    // UTILS ---
    private fun executeQuery(page: Int, perPage: Int, callback:(List<Project>) -> Unit) {
        networkState.postValue(NetworkState.Loading())
        scope.launch {
            try {
                val response = projectRepository.getGithubProjects(page, perPage)
                if(response.isSuccessful) {
                    val items = response.body()?.projects
                    if(items?.size!! >= 0) {
                        networkState.postValue(NetworkState.Success())
                        callback(items)
                    }
                }
                else networkState.postValue(NetworkState.Error(response.code()))
            }
            catch (exception : HttpException) {
                networkState.postValue(NetworkState.Error(exception.code()))
            }
        }
    }

    override fun loadBefore(params : LoadParams<Int>, callback : LoadCallback<Int, Project>) {
    }

    // PUBLIC API ---
    fun getNetworkState() : LiveData<NetworkState<Int>> = networkState
}