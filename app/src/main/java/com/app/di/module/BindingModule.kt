package com.app.di.module

import android.app.Application
import com.app.di.annotations.DataBinding
import com.app.ui.project.ProjectBinding
import com.app.utils.UiHelper
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder
import dagger.Module
import dagger.Provides

@Module
object BindingModule
{
    @Provides
    @DataBinding
    fun uiHelper(application: Application) = UiHelper(application)

    @Provides
    @DataBinding
    fun provideProjectBinding(fresco: PipelineDraweeControllerBuilder, uiHelper: UiHelper) : ProjectBinding =
        ProjectBinding(fresco, uiHelper)
}