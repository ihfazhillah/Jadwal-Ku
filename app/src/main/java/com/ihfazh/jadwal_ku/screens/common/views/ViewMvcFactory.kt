package com.ihfazh.jadwal_ku.screens.common.views

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ihfazh.jadwal_ku.screens.common.imageloader.ImageLoader
import com.ihfazh.jadwal_ku.screens.common.listviewhelper.AdjustHeightHelper
import com.ihfazh.jadwal_ku.screens.eventdetail.EventDetailMvcView
import com.ihfazh.jadwal_ku.screens.home.HomeMvcView
import com.ihfazh.jadwal_ku.screens.login.LoginViewMvc
import com.ihfazh.jadwal_ku.screens.main.MainViewMvc
import com.ihfazh.jadwal_ku.screens.settings.SettingsViewMvc
import javax.inject.Inject
import javax.inject.Provider

class ViewMvcFactory @Inject constructor(
    private val layoutInflater: Provider<LayoutInflater>,
    private val imageLoader: Provider<ImageLoader>,
    private val adjustHeightHelper: AdjustHeightHelper
) {
    fun newMainActivityMvc(): MainViewMvc {
        return MainViewMvc(layoutInflater.get())
    }

    fun newHomeMvc(parent: ViewGroup?) = HomeMvcView(
        layoutInflater.get(),
        parent,
        imageLoader.get(),
        adjustHeightHelper
    )

    fun newEventDetailMvc(container: ViewGroup?): EventDetailMvcView {
        return EventDetailMvcView(
            layoutInflater.get(),
            container,
            imageLoader.get()
        )
    }

    fun newLoginMvcView(container: ViewGroup?): LoginViewMvc {
        return LoginViewMvc(layoutInflater.get(), container)
    }

    fun newSettingsView(container: ViewGroup?): SettingsViewMvc {
        return SettingsViewMvc(layoutInflater.get(), container)
    }
}