package com.ihfazh.jadwal_ku.dependencyinjection.activity

import androidx.appcompat.app.AppCompatActivity
import com.ihfazh.jadwal_ku.screens.main.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)


    @Component.Builder
    interface Builder{
        @BindsInstance fun activity(activity: AppCompatActivity): Builder
        fun build(): ActivityComponent
    }
}