package com.app.pagination.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.app.model.main.Projects
import com.app.repository.ProjectsRepository
import kotlinx.coroutines.CoroutineScope

class ProjectsDataSourceFactory(private val projectsRepository : ProjectsRepository,
                                private val scope: CoroutineScope) : DataSource.Factory<Int, Projects>()
{
    val dataSource = MutableLiveData<ProjectsDataSource>()

    override fun create(): DataSource<Int, Projects> {

        val dataSource = ProjectsDataSource(projectsRepository , scope)
        this.dataSource.postValue(dataSource)
        return dataSource
    }
}