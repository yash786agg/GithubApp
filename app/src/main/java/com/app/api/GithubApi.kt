package com.app.api

import com.app.model.main.GithubApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    fun fetchGithubProjectsAsync(@Query("q") filter: String,
                                 @Query("per_page") perPage: Int,
                                 @Query("page") page: Int) : Deferred<Response<GithubApiResponse>>
}