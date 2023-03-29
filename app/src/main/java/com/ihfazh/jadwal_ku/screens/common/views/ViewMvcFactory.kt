package com.ihfazh.jadwal_ku.screens.common.views

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ihfazh.jadwal_ku.screens.common.imageloader.ImageLoader
import com.ihfazh.jadwal_ku.screens.eventdetail.EventDetailMvcView
import com.ihfazh.jadwal_ku.screens.home.HomeMvcView
import com.ihfazh.jadwal_ku.screens.main.MainViewMvc
import javax.inject.Inject
import javax.inject.Provider

class ViewMvcFactory @Inject constructor(
    private val layoutInflater: Provider<LayoutInflater>,
    private val imageLoader: Provider<ImageLoader>
) {
    fun newMainActivityMvc(): MainViewMvc {
        return MainViewMvc(layoutInflater.get())
    }

    fun newHomeMvc(parent: ViewGroup?) = HomeMvcView(
        layoutInflater.get(),
        parent,
        imageLoader.get()
    )

    fun newEventDetailMvc(container: ViewGroup?): EventDetailMvcView {
        return EventDetailMvcView(
            layoutInflater.get(),
            container,
            imageLoader.get()
        )
    }
}