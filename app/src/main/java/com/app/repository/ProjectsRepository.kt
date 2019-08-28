package com.app.repository

import com.app.api.GithubApi
import com.app.utils.Constants.Companion.perPage
import javax.inject.Inject

class ProjectsRepository @Inject constructor(private val githubApi : GithubApi) {

    suspend fun getGithubProjects(page : Int) = githubApi.fetchGithubProjectsAsync("user:square",perPage,page).await()
}