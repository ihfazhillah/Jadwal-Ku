package com.ihfazh.jadwal_ku.dependencyinjection.presentation

import com.ihfazh.jadwal_ku.screens.eventdetail.EventDetailFragment
import com.ihfazh.jadwal_ku.screens.home.HomeFragment
import com.ihfazh.jadwal_ku.screens.login.LoginFragment
import dagger.Component
import dagger.Subcomponent

@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {
    fun inject(fragment: HomeFragment)
    fun inject(fragment: EventDetailFragment)
    fun inject(loginFragment: LoginFragment)

}