package com.ihfazh.jadwal_ku.dependencyinjection.presentation

import com.ihfazh.jadwal_ku.screens.eventdetail.EventDetailFragment
import com.ihfazh.jadwal_ku.screens.eventlist.EventListFragment
import com.ihfazh.jadwal_ku.screens.home.HomeFragment
import com.ihfazh.jadwal_ku.screens.login.LoginFragment
import com.ihfazh.jadwal_ku.screens.settings.SettingsFragment
import com.ihfazh.jadwal_ku.screens.thumbnailview.ThumbnailViewFragment
import dagger.Component
import dagger.Subcomponent

@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {
    fun inject(fragment: HomeFragment)
    fun inject(fragment: EventDetailFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(thumbnailViewFragment: ThumbnailViewFragment)
    fun inject(eventListFragment: EventListFragment)



}