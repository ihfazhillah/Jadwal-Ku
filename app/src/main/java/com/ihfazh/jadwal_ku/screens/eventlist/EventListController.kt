package com.ihfazh.jadwal_ku.screens.eventlist

import com.ihfazh.jadwal_ku.dependencyinjection.MainDispatcher
import com.ihfazh.jadwal_ku.event.usecases.upcoming.GetUpcomingEventsUseCase
import com.ihfazh.jadwal_ku.event.usecases.upcoming.GetUpcomingEventsUseCase.UpcomingEventsResponse
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import kotlinx.coroutines.*
import javax.inject.Inject

class EventListController @Inject constructor(
    private val getUpcomingEventsTD: GetUpcomingEventsUseCase,
    private val screensNavigator: ScreensNavigator,
    @MainDispatcher dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : EventListViewMvc.Listener {
    private val coroutineScope = CoroutineScope(dispatcher)

    private lateinit var viewMvc: EventListViewMvc

    fun bindView(viewMvc: EventListViewMvc){
        this.viewMvc = viewMvc
    }

    fun onStart(){
        viewMvc.registerListener(this)
        fetchUpcomingEvents()
    }

    private fun fetchUpcomingEvents() {
        viewMvc.showLoadingIndicator()
        coroutineScope.launch {
            val resp = getUpcomingEventsTD.getUpcomingEvents()
            if (resp is UpcomingEventsResponse.Success) {
                viewMvc.bindEvents(resp.events)
            }
            viewMvc.hideLoadingIndicator()
        }
    }


    fun onStop(){
        coroutineScope.coroutineContext.cancelChildren()
        viewMvc.unregisterListener(this)
    }

    override fun onEventClick(eventId: String) {
        screensNavigator.goToEventDetail(eventId)
    }
}