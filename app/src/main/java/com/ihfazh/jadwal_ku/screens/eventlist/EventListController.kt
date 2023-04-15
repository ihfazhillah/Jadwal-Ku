package com.ihfazh.jadwal_ku.screens.eventlist

import android.util.Log
import com.ihfazh.jadwal_ku.dependencyinjection.MainDispatcher
import com.ihfazh.jadwal_ku.event.usecases.upcoming.GetUpcomingEventsUseCase
import com.ihfazh.jadwal_ku.event.usecases.upcoming.GetUpcomingEventsUseCase.UpcomingEventsResponse
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import kotlinx.coroutines.*
import javax.inject.Inject

class EventListController @Inject constructor(
    private val getUpcomingEventsTD: GetUpcomingEventsUseCase,
    private val screensNavigator: ScreensNavigator,
    private val eventListStateManager: EventListStateManager,
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
        eventListStateManager.loading = true
        coroutineScope.launch {
            val resp = getUpcomingEventsTD.getUpcomingEvents(limit = EVENTS_LIMIT, page = eventListStateManager.page)
            if (resp is UpcomingEventsResponse.Success) {
                eventListStateManager.hasNext = resp.hasNext
                viewMvc.bindEvents(resp.events)
            }
            eventListStateManager.loading = false
            viewMvc.hideLoadingIndicator()
        }
    }


    fun onStop(){
        coroutineScope.coroutineContext.cancelChildren()
        viewMvc.unregisterListener(this)
        eventListStateManager.reset()
    }

    override fun onEventClick(eventId: String) {
        screensNavigator.goToEventDetail(eventId)
    }

    override fun onLastEventItemReached() {
        if (eventListStateManager.canGoNext()){
            eventListStateManager.nextPage()
            fetchUpcomingEvents()
        }
    }

    companion object {
        private const val EVENTS_LIMIT = 5
    }
}