package com.ihfazh.jadwal_ku.screens.eventdetail

import com.ihfazh.jadwal_ku.event.EventLink
import com.ihfazh.jadwal_ku.event.EventProvider
import com.ihfazh.jadwal_ku.event.EventUrlType
import com.ihfazh.jadwal_ku.event.GetEventDetailUseCase
import com.ihfazh.jadwal_ku.screens.common.intenthelper.IntentHelper
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

private const val EVENT_ID = "10"

private const val ZOOM_LINK = "https://zoom.com.us/xxxx"

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class EventDetailControllerTest {

    private lateinit var SUT: EventDetailController
    private lateinit var eventDetailUseCase: GetEventDetailUseCaseTD
    private lateinit var dispatcher: CoroutineDispatcher

    @Mock lateinit var viewMvc: EventDetailMvcView
    @Mock lateinit var intentHelperMock: IntentHelper
    @Mock lateinit var navigatorMock: ScreensNavigator

    @Before
    fun setUp() {
        eventDetailUseCase = GetEventDetailUseCaseTD()
        dispatcher = UnconfinedTestDispatcher()

        SUT = EventDetailController(
            eventDetailUseCase,
            intentHelperMock,
            navigatorMock,
            dispatcher
        )
        SUT.bindView(viewMvc, EVENT_ID)
    }

    @Test
    fun `onStart should register listener`(){
        SUT.onStart()
        verify(viewMvc).registerListener(SUT)
    }

    @Test
    fun `onStop should unregister listener`(){
        SUT.onStop()
        verify(viewMvc).unregisterListener(SUT)
    }

    @Test
    fun `onStart should hide all data`(){
        SUT.onStart()
        verify(viewMvc).hideEventData()
        verify(viewMvc).hideErrorIndicator()
    }

    @Test
    fun `onStart should call getDetailEvent`() = runTest{
        SUT.onStart()
        assertEquals(eventDetailUseCase.eventId, EVENT_ID)
    }

    @Test
    fun `onStart getDetailEvent should display loading indicator`(){
        SUT.onStart()
        verify(viewMvc).showLoadingIndicator()
    }

    @Test
    fun `onStart getDetailEvent success call bindEvent`() = runTest {
        SUT.onStart()
        advanceUntilIdle()
        verify(viewMvc).bindEvent(EventProvider.provideEvent())
    }

    @Test
    fun `onStart getDetailEvent success call bindOpenButton with youtuble link`() = runTest {
        SUT.onStart()
        advanceUntilIdle()
        verify(viewMvc).bindOpenButton(EventUrlType.Youtube)
    }

    @Test
    fun `onStart getDetailEvent success call bindOpenButton with zoom link`() = runTest {
        eventDetailUseCase.onlyZoomLink = true
        SUT.onStart()
        advanceUntilIdle()
        verify(viewMvc).bindOpenButton(EventUrlType.Zoom)
    }

    @Test
    fun `onStart getDetailEvent success don't call bindOpenButton if no link`() = runTest {
        eventDetailUseCase.noLink = true
        SUT.onStart()
        advanceUntilIdle()
        verify(viewMvc).bindOpenButton(EventUrlType.Empty)
    }

    @Test
    fun `onStart getDetailEventSuccess show event data`() = runTest{
        SUT.onStart()
        advanceUntilIdle()
        verify(viewMvc).showEventData()
    }

    @Test
    fun `onStart getDetailEvent finished hide loading indicator`() = runTest{
        SUT.onStart()
        advanceUntilIdle()
        verify(viewMvc).hideLoadingIndicator()
    }

    @Test
    fun `onStart getDetailEvent general error display error indicator`() = runTest{
        eventDetailUseCase.generalError = true
        SUT.onStart()
        advanceUntilIdle()
        verify(viewMvc).showErrorIndicator()
    }

    @Test
    fun `onStart getDetailEvent network error display error indicator`() = runTest{
        eventDetailUseCase.networkError = true
        SUT.onStart()
        advanceUntilIdle()
        verify(viewMvc).showErrorIndicator()
    }

    @Test
    fun `onOpenButtonClick should open youtube url`() = runTest {
        SUT.onStart()
        advanceUntilIdle()
        SUT.onOpenButtonClick()
        verify(intentHelperMock).openUrl((EventProvider.provideEvent().link as EventLink.YoutubeLink).link)
    }

    @Test
    fun `onOpenButtonClick should open zoom url`() = runTest {
        eventDetailUseCase.onlyZoomLink = true
        SUT.onStart()
        advanceUntilIdle()
        SUT.onOpenButtonClick()
        verify(intentHelperMock).openUrl(ZOOM_LINK)
    }

    @Test
    fun `onRetryButtonClick should retry get detail flow`() = runTest {
        SUT.onRetryButtonClick()
        verify(viewMvc).showLoadingIndicator()
        advanceUntilIdle()
        verify(viewMvc).hideLoadingIndicator()
    }

    @Test
    fun `onBackClick should navigate back`(){
        SUT.onBackClick()
        verify(navigatorMock).navigateUp()
    }


    inner class GetEventDetailUseCaseTD: GetEventDetailUseCase{
        var generalError: Boolean = false
        var networkError = false
        var onlyZoomLink: Boolean = false
        var noLink = false
        var eventId: String? = null

        override suspend fun getDetail(id: String): GetEventDetailUseCase.Result {
            eventId = id

            if (generalError){
                return GetEventDetailUseCase.Result.GeneralError
            }

            if (networkError) return GetEventDetailUseCase.Result.NetworkError

            var event = EventProvider.provideEvent()
            if (onlyZoomLink){
                event = event.copy(link = EventLink.ZoomLink(ZOOM_LINK))
            }
            if (noLink){
                event = event.copy(link=EventLink.EmptyLink)
            }
            return GetEventDetailUseCase.Result.Success(event)
        }

    }
}