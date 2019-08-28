package com.app.di.component

import android.app.Application
import androidx.databinding.DataBindingComponent
import com.app.di.annotations.DataBinding
import com.app.di.module.ActivityBuilderModule
import com.app.di.module.AppModule
import com.app.di.module.ViewModelFactoryModule
import com.app.nandroid.BaseApplication
import javax.inject.Singleton
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Singleton
@DataBinding
@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuilderModule::class, AppModule::class
    , ViewModelFactoryModule::class/*, BindingModule::class*/])
interface AppComponent : AndroidInjector<BaseApplication> , DataBindingComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

        @BindsInstance
        fun fresco(fresco: PipelineDraweeControllerBuilder): Builder
        //fun bindingModule(bindingModule: BindingModule): Builder
    }
    //override fun getMainBindingAdapter(): MainBindingAdapter
}
