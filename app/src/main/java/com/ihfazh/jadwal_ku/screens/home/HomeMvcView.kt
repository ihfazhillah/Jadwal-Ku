package com.ihfazh.jadwal_ku.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.event.Event
import com.ihfazh.jadwal_ku.screens.common.views.BaseMvcView
import javax.inject.Inject

class HomeMvcView(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
): BaseMvcView<HomeMvcView.Listener>(layoutInflater, parent, R.layout.layout_home) {
    fun showCurrentEventIndicator() {
//        TODO("Not yet implemented")
    }

    fun hideCurrentEventIndicator() {
//        TODO("Not yet implemented")
    }

    fun bindCurrentEvent(event: Event) {
//        TODO("Not yet implemented")
    }

    fun showCurrentEventEmpty() {
//        TODO("Not yet implemented")
    }

    interface Listener{

    }

}