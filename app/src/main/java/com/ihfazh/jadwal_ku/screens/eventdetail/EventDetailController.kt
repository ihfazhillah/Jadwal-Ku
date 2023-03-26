package com.ihfazh.jadwal_ku.screens.eventdetail

import com.ihfazh.jadwal_ku.event.Event
import com.ihfazh.jadwal_ku.event.EventUrlType
import com.ihfazh.jadwal_ku.event.GetEventDetailUseCase
import com.ihfazh.jadwal_ku.screens.common.intenthelper.IntentHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EventDetailController (
    private val getEventDetailUseCase: GetEventDetailUseCase,
    private val intentHelper: IntentHelper,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : EventDetailMvcView.Listener {

    private val coroutineScope = CoroutineScope(dispatcher)
    private lateinit var viewMvc: EventDetailMvcView

    private var questionId : String? = null
    private var detail: Event? = null

    fun bindView(view: EventDetailMvcView, questionId: String){
        viewMvc = view
        this.questionId = questionId
    }


    fun onStart(){
        viewMvc.registerListener(this)
        viewMvc.hideEventData()
        viewMvc.hideErrorIndicator()
        getEventDetail()
    }

    fun onStop(){
        viewMvc.unregisterListener(this)

    }

    override fun onOpenButtonClick(urlType: EventUrlType) {
        val url = when(urlType){
            EventUrlType.Youtube -> detail!!.youtubeLink!!
            EventUrlType.Zoom -> detail!!.zoomLink!!
            EventUrlType.Empty -> null
        }

        if (url != null){
            intentHelper.openUrl(url)
        }
    }

    override fun onRetryButtonClick() {
        getEventDetail()
    }

    private fun getEventDetail() {
        viewMvc.showLoadingIndicator()
        coroutineScope.launch {
            when(val result = getEventDetailUseCase.getDetail(questionId!!)){
                GetEventDetailUseCase.Result.GeneralError -> {
                    viewMvc.showErrorIndicator()
                }
                GetEventDetailUseCase.Result.NetworkError -> {
                    viewMvc.showErrorIndicator()
                }
                is GetEventDetailUseCase.Result.Success -> {
                    detail = result.event

                    viewMvc.bindEvent(result.event)
                    if (result.event.youtubeLink != null){
                        viewMvc.bindOpenButton(EventUrlType.Youtube)
                    } else if (result.event.zoomLink != null){
                        viewMvc.bindOpenButton(EventUrlType.Zoom)
                    } else {
                        viewMvc.bindOpenButton(EventUrlType.Empty)
                    }
                    viewMvc.showEventData()
                }
            }

            viewMvc.hideLoadingIndicator()
        }
    }
}