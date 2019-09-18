package com.app.api

import com.app.model.project.ProjectApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProjectApi {

    @GET("search/repositories")
    fun getProjectsAsync(@Query("q") filter: String,
                         @Query("per_page") perPage: Int,
                         @Query("page") page : Int) : Deferred<Response<ProjectApiResponse>>
}