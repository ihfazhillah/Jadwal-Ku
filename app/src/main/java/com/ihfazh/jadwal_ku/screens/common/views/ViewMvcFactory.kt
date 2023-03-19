package com.ihfazh.jadwal_ku.screens.common.views

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ihfazh.jadwal_ku.screens.home.HomeMvcView
import com.ihfazh.jadwal_ku.screens.main.MainViewMvc
import javax.inject.Inject
import javax.inject.Provider

class ViewMvcFactory @Inject constructor(
    private val layoutInflater: Provider<LayoutInflater>
) {
    fun newMainActivityMvc(): MainViewMvc {
        return MainViewMvc(layoutInflater.get())
    }

    fun newHomeMvc(parent: ViewGroup?) = HomeMvcView(layoutInflater.get(), parent)
}