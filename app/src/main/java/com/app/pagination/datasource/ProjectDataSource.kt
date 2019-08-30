package com.app.pagination.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.app.api.NetworkState
import com.app.model.project.Project
import com.app.repository.ProjectRepository
import com.app.utils.Constants.Companion.noMoreData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProjectDataSource(private val projectRepository : ProjectRepository,
                        private val scope: CoroutineScope) : PageKeyedDataSource<Int, Project>()
{
    private val TAG : String = "ProjectListActivity"

    // FOR DATA ---
    private val networkState = MutableLiveData<NetworkState<Int>>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Project>) {

        Log.e(TAG, "loadInitial")

        networkState.postValue(NetworkState.Loading())

        scope.launch {
            try
            {
                val response = projectRepository.getGithubProjects(1)

                Log.e(TAG, "loadInitial code: ${response.code()}")
                Log.e(TAG, "loadInitial message: ${response.message()}")
                Log.e(TAG, "loadInitial isSuccessful: ${response.isSuccessful}")
                Log.e(TAG, "loadInitial response: ${response.body().toString()}")

                if(response.isSuccessful) {

                    if(response.body() != null)
                    {
                        val items = response.body()?.projects

                        if(items != null)
                            callback.onResult(items, null, 2)

                        Log.e(TAG, "loadInitial Response totalCount: ${response.body()?.totalCount}")
                        Log.e(TAG, "loadInitial Response size: ${items!!.size}")
                    }

                    networkState.postValue(NetworkState.Success())
                }
                else
                    networkState.postValue(NetworkState.Error(response.code()))
            }
            catch (exception : HttpException) {
                Log.e(TAG, "loadInitial Exception: $exception")
                networkState.postValue(NetworkState.Error(exception.code()))
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Project>) {

        networkState.postValue(NetworkState.Loading())

        scope.launch {
            try
            {
                Log.e(TAG, "loadAfter key: ${params.key}")

                val response = projectRepository.getGithubProjects(params.key)

                Log.e(TAG, "loadAfter code: ${response.code()}")
                Log.e(TAG, "loadAfter message: ${response.message()}")
                Log.e(TAG, "loadAfter isSuccessful: ${response.isSuccessful}")
                Log.e(TAG, "loadAfter response: ${response.body().toString()}")

                if(response.isSuccessful) {
                    val nextKey = (if(params.key == response.body()?.totalCount) null else params.key + 1)

                    if(response.body() != null)
                    {
                        val items = response.body()?.projects

                        if(items?.size!! >= 0) {

                            networkState.postValue(NetworkState.Success())
                            callback.onResult(items, nextKey)
                        }
                        else
                            networkState.postValue(NetworkState.Error(noMoreData))

                        Log.e(TAG, "loadAfter Response totalCount: ${response.body()?.totalCount}")
                        Log.e(TAG, "loadAfter Response size: ${items.size}")
                    }
                }
                else
                    networkState.postValue(NetworkState.Error(response.code()))
            }
            catch (exception : HttpException) {
                Log.e(TAG, "loadAfter Exception: $exception")
                networkState.postValue(NetworkState.Error(exception.code()))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Project>) {}

    // PUBLIC API ---
    fun getNetworkState(): LiveData<NetworkState<Int>> = networkState
}