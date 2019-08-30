package com.app.di.module

import com.app.di.module.project.ProjectModule
import com.app.di.module.project.ProjectScope
import com.app.di.module.project.ProjectListViewModelModule
import com.app.ui.project.details.ProjectDetailsActivity
import com.app.ui.project.list.ProjectListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityBuilderModule {

    @ProjectScope
    @ContributesAndroidInjector(modules = [ProjectListViewModelModule::class, ProjectModule::class])
    abstract fun getProjectsListActivity(): ProjectListActivity

    @ProjectScope
    @ContributesAndroidInjector()
    abstract fun getProjectDetailsActivity(): ProjectDetailsActivity
}