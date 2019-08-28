package com.app.pagination.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.app.api.NetworkState
import com.app.model.main.Projects
import com.app.repository.ProjectsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ProjectsDataSource(private val projectsRepository : ProjectsRepository,
                         private val scope: CoroutineScope) : PageKeyedDataSource<Int, Projects>()
{
    private val TAG : String = "MainActivity"

    // FOR DATA ---
    private val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Projects>) {

        Log.e(TAG, "loadInitial")

        networkState.postValue(NetworkState.LOADING)

        scope.launch {
            try
            {
                val response = projectsRepository.getGithubProjects(1)

                when {
                    response.isSuccessful -> {

                        if(response.body() != null)
                        {
                            val items = response.body()?.projects

                            if(items != null)
                                callback.onResult(items, null, 2)

                            Log.e(TAG, "loadInitial Response totalCount: ${response.body()?.totalCount}")
                            Log.e(TAG, "loadInitial Response totalCount: ${items!![1].fullName}")
                        }

                        networkState.postValue(NetworkState.SUCCESS)
                    }
                }
            }
            catch (exception : Exception) {
                Log.e(TAG, "loadInitial Exception: $exception")
                networkState.postValue(NetworkState.ERROR)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Projects>) {

        Log.e(TAG, "loadAfter")

        networkState.postValue(NetworkState.LOADING)

        scope.launch {
            try
            {
                val response = projectsRepository.getGithubProjects(params.key)

                when {
                    response.isSuccessful -> {

                        val nextKey = (if(params.key == response.body()?.totalCount) null else params.key + 1)

                        if(response.body() != null)
                        {
                            val items = response.body()?.projects

                            if(items != null)
                                callback.onResult(items, nextKey)

                            Log.e(TAG, "loadAfter Response totalCount: ${response.body()?.totalCount}")
                            Log.e(TAG, "loadAfter Response totalCount: ${items!![1].fullName}")
                        }

                        networkState.postValue(NetworkState.SUCCESS)
                    }
                }
            }
            catch (exception : Exception) {
                Log.e(TAG, "loadAfter Exception: $exception")
                networkState.postValue(NetworkState.ERROR)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Projects>) {}

    // PUBLIC API ---

    fun getNetworkState(): LiveData<NetworkState> = networkState
}