package com.ihfazh.jadwal_ku.screens.eventdetail

import com.ihfazh.jadwal_ku.dependencyinjection.MainDispatcher
import com.ihfazh.jadwal_ku.event.Event
import com.ihfazh.jadwal_ku.event.EventLink
import com.ihfazh.jadwal_ku.event.EventUrlType
import com.ihfazh.jadwal_ku.event.GetEventDetailUseCase
import com.ihfazh.jadwal_ku.screens.common.intenthelper.IntentHelper
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import kotlinx.coroutines.*
import javax.inject.Inject


class EventDetailController @Inject constructor(
    private val getEventDetailUseCase: GetEventDetailUseCase,
    private val intentHelper: IntentHelper,
    private val navigator: ScreensNavigator,

    @MainDispatcher dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
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
        coroutineScope.coroutineContext.cancelChildren()

    }

    override fun onOpenButtonClick() {
        if (detail !== null){
            when(val link = detail!!.link){
                EventLink.EmptyLink -> {
                    // noop
                }
                is EventLink.YoutubeLink -> intentHelper.openUrl(link.link)
                is EventLink.ZoomLink -> intentHelper.openUrl(link.link)
            }
        }
    }

    override fun onRetryButtonClick() {
        getEventDetail()
    }

    override fun onBackClick() {
        navigator.navigateUp()
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
                    when(result.event.link){
                        EventLink.EmptyLink -> viewMvc.bindOpenButton(EventUrlType.Empty)
                        is EventLink.YoutubeLink -> viewMvc.bindOpenButton(EventUrlType.Youtube)
                        is EventLink.ZoomLink -> viewMvc.bindOpenButton(EventUrlType.Zoom)
                    }
                    viewMvc.showEventData()
                }
            }

            viewMvc.hideLoadingIndicator()
        }
    }
}