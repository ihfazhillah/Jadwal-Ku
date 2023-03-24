package com.ihfazh.jadwal_ku.dependencyinjection.activity

import androidx.appcompat.app.AppCompatActivity
import com.ihfazh.jadwal_ku.dependencyinjection.presentation.PresentationComponent
import com.ihfazh.jadwal_ku.dependencyinjection.presentation.PresentationModule
import com.ihfazh.jadwal_ku.screens.main.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)

    fun newPresentationComponent(presentationModule: PresentationModule): PresentationComponent


    @Subcomponent.Builder
    interface Builder{
        @BindsInstance fun activity(activity: AppCompatActivity): Builder
        fun build(): ActivityComponent
    }
}