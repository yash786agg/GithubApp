package com.app.repository

import com.app.api.ProjectApi
import com.app.utils.Constants.Companion.PER_PAGE
import com.app.utils.Constants.Companion.PROJECT_FILTER
import javax.inject.Inject

class ProjectRepository @Inject constructor(private val projectApi : ProjectApi) {

    suspend fun getGithubProjects() = projectApi.getProjectsAsync(PROJECT_FILTER,PER_PAGE).await()
}