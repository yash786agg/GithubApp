package com.app.di.module.project

import androidx.lifecycle.ViewModel
import com.app.di.annotations.ViewModelKey
import com.app.ui.project.list.ProjectListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProjectListViewModelModule
{
    @Binds
    @IntoMap
    @ViewModelKey(ProjectListViewModel::class)
    abstract fun bindProjectListViewModel(viewModel: ProjectListViewModel): ViewModel
}