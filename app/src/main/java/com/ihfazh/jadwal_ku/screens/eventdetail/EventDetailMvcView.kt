package com.ihfazh.jadwal_ku.screens.eventdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.event.Event
import com.ihfazh.jadwal_ku.screens.common.views.BaseMvcView

class EventDetailMvcView(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
): BaseMvcView<EventDetailMvcView.Listener>(layoutInflater, parent, R.layout.layout_event_detail) {

    interface Listener{
        fun onOpenButtonClick(url: String)
        fun onRetryButtonClick()
    }


    fun hideEventData() {
        TODO("Not yet implemented")
    }

    fun hideErrorIndicator() {
        TODO("Not yet implemented")
    }

    fun showLoadingIndicator() {
        TODO("Not yet implemented")
    }

    fun bindEvent(event: Event) {
        TODO("Not yet implemented")
    }

    fun bindOpenButton(link: String) {
        TODO("Not yet implemented")
    }

    fun showEventData() {
        TODO("Not yet implemented")
    }

    fun hideLoadingIndicator() {
        TODO("Not yet implemented")
    }

    fun showErrorIndicator() {
        TODO("Not yet implemented")
    }


}