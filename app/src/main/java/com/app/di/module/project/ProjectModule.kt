package com.app.di.module.project

import com.app.nandroid.api.ProjectApi
import com.app.repository.ProjectRepository
import com.app.ui.project.list.adapter.ProjectListAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ProjectModule
{
    @Module
    companion object
    {
        @ProjectScope
        @Provides
        @JvmStatic
        fun provideProjectApi(retrofit: Retrofit) : ProjectApi = retrofit.create(ProjectApi::class.java)

        @ProjectScope
        @Provides
        @JvmStatic
        fun provideProjectListAdapter() = ProjectListAdapter()

        @ProjectScope
        @Provides
        @JvmStatic
        fun provideProjectRepository(projectApi : ProjectApi) = ProjectRepository(projectApi)
    }
}