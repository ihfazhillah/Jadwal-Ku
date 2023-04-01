package com.ihfazh.jadwal_ku.screens.home

import com.ihfazh.jadwal_ku.dependencyinjection.MainDispatcher
import com.ihfazh.jadwal_ku.event.usecases.current.GetCurrentEventUseCase
import com.ihfazh.jadwal_ku.event.usecases.current.GetCurrentEventUseCase.CurrentEventResponse
import com.ihfazh.jadwal_ku.event.usecases.upcoming.GetUpcomingEventsUseCase
import com.ihfazh.jadwal_ku.event.usecases.upcoming.GetUpcomingEventsUseCase.UpcomingEventsResponse
import com.ihfazh.jadwal_ku.screens.common.ToastHelper
import com.ihfazh.jadwal_ku.screens.common.intenthelper.IntentHelper
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import kotlinx.coroutines.*
import javax.inject.Inject

class HomeController @Inject constructor(
    private val getCurrentUseCase: GetCurrentEventUseCase,
    private val getUpcomingEventsUseCase: GetUpcomingEventsUseCase,
    private val toastHelper: ToastHelper,
    private val intentHelper: IntentHelper,
    private val screensNavigator: ScreensNavigator,
    @MainDispatcher dispatcher: CoroutineDispatcher = Dispatchers.Main
) : HomeMvcView.Listener {

    private val coroutineScope = CoroutineScope(dispatcher)

    private lateinit var viewMvc: HomeMvcView

    fun bindView(viewMvc: HomeMvcView){
        this.viewMvc = viewMvc
    }

    fun onStart(){
        this.viewMvc.registerListener(this)

        getCurrentEvent()
        getUpcomingEvents()
    }


    fun onStop(){
        viewMvc.unregisterListener(this)

        // not sure how to test the cancellation
        coroutineScope.coroutineContext.cancelChildren()
    }

    override fun onReloadClick() {
        getCurrentEvent()
    }

    override fun onOpenClick(url: String) {
        intentHelper.openUrl(url)
    }

    override fun onUpcomingClick(id: String) {
        screensNavigator.goToEventDetail(id)
    }

    private fun getCurrentEvent() {
        viewMvc.hideCurrentEventEmpty()
        viewMvc.hideCurrentEvent()
        viewMvc.showCurrentEventIndicator()
        coroutineScope.launch {
            when (val eventResponse = getCurrentUseCase.getCurrent()) {
                CurrentEventResponse.EmptyEvent -> {
                    viewMvc.showCurrentEventEmpty()
                }
                CurrentEventResponse.GeneralError -> {
                    viewMvc.showCurrentEventEmpty()
                    toastHelper.showGenericError()
                }
                CurrentEventResponse.NetworkError -> {
                    viewMvc.showCurrentEventEmpty()
                    toastHelper.showNetworkError()
                }
                is CurrentEventResponse.Success -> {
                    viewMvc.bindCurrentEvent(eventResponse.event)
                    viewMvc.showCurrentEvent()
                }
            }
            viewMvc.hideCurrentEventIndicator()
        }
    }

    private fun getUpcomingEvents() {
        viewMvc.hideUpcomingEventList()
        viewMvc.showUpcomingEventLoadingIndicator()
        viewMvc.hideUpcomingNoData()
        coroutineScope.launch {
            when(val response = getUpcomingEventsUseCase.getUpcomingEvents()){
                UpcomingEventsResponse.EmptyEvent -> {
                    viewMvc.showUpcomingEventNoData()
                }
                UpcomingEventsResponse.GeneralError -> {
                    viewMvc.showUpcomingEventNoData()
                    toastHelper.showGenericError()
                }
                UpcomingEventsResponse.NetworkError -> {
                    viewMvc.showUpcomingEventNoData()
                    toastHelper.showNetworkError()
                }
                is UpcomingEventsResponse.Success -> {
                    viewMvc.bindUpcomingEvents(response.events)
                    viewMvc.showUpcomingEventList()
                }
            }

            viewMvc.hideUpcomingEventLoadingIndicator()
        }
    }

}