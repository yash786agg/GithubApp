package com.app.repository

import com.app.api.ProjectApi
import com.app.utils.Constants.Companion.PROJECT_FILTER

class ProjectRepository(private val projectApi : ProjectApi) {

    suspend fun getGithubProjects(page : Int, perPage : Int) = projectApi.getProjectsAsync(PROJECT_FILTER,perPage,page).await()
}