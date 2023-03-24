package com.ihfazh.jadwal_ku.dependencyinjection.app

import android.app.Application
import com.ihfazh.jadwal_ku.MyApplication
import com.ihfazh.jadwal_ku.dependencyinjection.activity.ActivityComponent
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(application: MyApplication)

    fun newActivityComponent(): ActivityComponent.Builder

    @Component.Builder
    interface Builder{
        @BindsInstance fun application(application: Application): Builder
        fun build(): AppComponent
    }
}