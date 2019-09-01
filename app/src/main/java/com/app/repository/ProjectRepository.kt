package com.app.repository

import com.app.api.ProjectApi
import com.app.utils.Constants.Companion.perPage
import com.app.utils.Constants.Companion.projectFilter
import javax.inject.Inject

class ProjectRepository @Inject constructor(private val projectApi : ProjectApi) {

    suspend fun getGithubProjects() = projectApi.getProjectsAsync(projectFilter,perPage).await()
}