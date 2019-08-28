package com.app.di.module.main

import com.app.api.GithubApi
import com.app.repository.ProjectsRepository
import com.app.ui.main.adapter.MainAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule
{
    @Module
    companion object
    {
        @MainScope
        @Provides
        @JvmStatic
        fun provideGithubApi(retrofit: Retrofit) : GithubApi = retrofit.create(GithubApi::class.java)

        @MainScope
        @Provides
        @JvmStatic
        fun provideMainAdapter() = MainAdapter()

        @MainScope
        @Provides
        @JvmStatic
        fun provideProjectsRepository(githubApi : GithubApi) = ProjectsRepository(githubApi)
    }
}