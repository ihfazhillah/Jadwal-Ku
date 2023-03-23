package com.ihfazh.jadwal_ku.screens.home

import com.ihfazh.jadwal_ku.dependencyinjection.MainDispatcher
import com.ihfazh.jadwal_ku.event.CurrentEventResponse
import com.ihfazh.jadwal_ku.event.GetCurrentEventUseCase
import com.ihfazh.jadwal_ku.screens.common.ToastHelper
import kotlinx.coroutines.*
import javax.inject.Inject

class HomeController @Inject constructor(
    private val getCurrentUseCase: GetCurrentEventUseCase,
    private val toastHelper: ToastHelper,
    @MainDispatcher dispatcher: CoroutineDispatcher = Dispatchers.Main
) : HomeMvcView.Listener {

    private val coroutineScope = CoroutineScope(dispatcher)

    private lateinit var viewMvc: HomeMvcView

    fun bindView(viewMvc: HomeMvcView){
        this.viewMvc = viewMvc
    }

    fun onStart(){
        this.viewMvc.registerListener(this)
        initializeCurrentEvent()
    }

    fun onStop(){
        viewMvc.unregisterListener(this)

        // not sure how to test the cancellation
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun initializeCurrentEvent() {
        this.viewMvc.showCurrentEventIndicator()
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
                }
            }
            viewMvc.hideCurrentEventIndicator()
        }
    }

}