package com.app.di

import com.app.githubapp.BuildConfig
import com.app.api.ProjectApi
import com.app.network.createNetworkClient
import com.app.repository.ProjectRepository
import com.app.ui.project.list.ProjectListViewModel
import com.app.utils.UiHelper
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(viewModelModule,
            repositoryModule,
            networkModule,
            uiHelperModule)
    )
}

val viewModelModule = module {
    viewModel { ProjectListViewModel(projectRepository = get()) }
}

val repositoryModule = module {
    single { ProjectRepository(projectApi = get()) }
}

val networkModule = module {
    single { usersApi }
}

val uiHelperModule = module {
    single { UiHelper(androidContext()) }
}

private val retrofit: Retrofit = createNetworkClient(BuildConfig.BASE_URL)

private val usersApi : ProjectApi = retrofit.create(ProjectApi::class.java)
