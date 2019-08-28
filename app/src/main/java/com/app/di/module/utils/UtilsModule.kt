package com.app.di.module.utils

import android.app.Application
import com.app.utils.UiHelper
import dagger.Module
import dagger.Provides

@Module
class UtilsModule
{
    @Module
    companion object
    {
        @Provides
        @JvmStatic
        fun uiHelper(application: Application) = UiHelper(application)
    }
}