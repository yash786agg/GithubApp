package com.app.pagination.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.app.model.project.Project
import com.app.repository.ProjectRepository
import kotlinx.coroutines.CoroutineScope

class ProjectDataSourceFactory(private val projectRepository : ProjectRepository,
                               private val scope: CoroutineScope) : DataSource.Factory<Int, Project>()
{
    val dataSource = MutableLiveData<ProjectDataSource>()

    override fun create() : DataSource<Int, Project> {

        val dataSource = ProjectDataSource(projectRepository , scope)
        this.dataSource.postValue(dataSource)
        return dataSource
    }
}