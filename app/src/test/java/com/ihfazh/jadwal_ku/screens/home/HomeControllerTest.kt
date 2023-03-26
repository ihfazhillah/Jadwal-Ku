package com.ihfazh.jadwal_ku.screens.home

import com.ihfazh.jadwal_ku.event.*
import com.ihfazh.jadwal_ku.event.GetCurrentEventUseCase.*
import com.ihfazh.jadwal_ku.event.GetUpcomingEventsUseCase.*
import com.ihfazh.jadwal_ku.screens.common.ToastHelper
import com.ihfazh.jadwal_ku.screens.common.intenthelper.IntentHelper
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class HomeControllerTest{
    private lateinit var SUT: HomeController
    private lateinit var getCurrentUseCaseTD: GetCurrentUseCaseTD
    private lateinit var getUpcomingEventsTD: GetUpcomingEventUseCaseTD
    private lateinit var dispatcher: TestDispatcher

    @Mock private lateinit var homeMvcViewMock: HomeMvcView
    @Mock private lateinit var toastHelperMock: ToastHelper
    @Mock private lateinit var intentHelperMock: IntentHelper
    @Mock private lateinit var screensNavigatorMock: ScreensNavigator

    @Before
    fun setUp(){
        getCurrentUseCaseTD = GetCurrentUseCaseTD()
        getUpcomingEventsTD = GetUpcomingEventUseCaseTD()
        dispatcher = UnconfinedTestDispatcher()

        SUT = HomeController(
            getCurrentUseCaseTD,
            getUpcomingEventsTD,
            toastHelperMock,
            intentHelperMock,
            screensNavigatorMock,
            dispatcher
        )
        SUT.bindView(homeMvcViewMock)
    }

    @Test
    fun `onStart register viewMvc listeners`(){
        SUT.onStart()
        verify(homeMvcViewMock).registerListener(SUT)
    }

    @Test
    fun `onStop unRegister viewMvc listeners`(){
        SUT.onStop()
        verify(homeMvcViewMock).unregisterListener(SUT)
    }


    @Test
    fun `onStart display the loading state in the now card`() = runTest{
        SUT.onStart()
        verify(homeMvcViewMock, times(1)).showCurrentEventIndicator()
    }

    @Test
    fun `onStart getCurrentEvent called`() = runTest {
        SUT.onStart()
        assertEquals(getCurrentUseCaseTD.callCounts, 1)
    }

    @Test
    fun `onStart getCurrentEvent finished call hideCurrentEventIndicator`() = runTest {
        SUT.onStart()
        advanceUntilIdle()
        verify(homeMvcViewMock, times(1)).hideCurrentEventIndicator()
    }

    @Test
    fun `onStart getCurrentEvent success call view to display data`() = runTest {
        SUT.onStart()
        verify(homeMvcViewMock).bindCurrentEvent(EventProvider.provideEvent())
        verify(homeMvcViewMock).showCurrentEvent()
    }

    @Test
    fun `onStart getCurrentEvent empty call display empty data`() = runTest{
        getCurrentUseCaseTD.empty = true
        SUT.onStart()
        verify(homeMvcViewMock).showCurrentEventEmpty()
    }

    @Test
    fun `onStart getCurrentEvent generic error call display empty data and toast`() = runTest{
        getCurrentUseCaseTD.genericError = true
        SUT.onStart()
        verify(homeMvcViewMock).showCurrentEventEmpty()
        verify(toastHelperMock).showGenericError()
    }

    @Test
    fun `onStart getCurrentEvent network error call display empty data and toast`() = runTest{
        getCurrentUseCaseTD.networkError = true
        SUT.onStart()
        verify(homeMvcViewMock).showCurrentEventEmpty()
        verify(toastHelperMock).showNetworkError()
    }

    @Test
    fun `onReloadClick call get current use case`() = runTest {
        SUT.onReloadClick()
        assertEquals(getCurrentUseCaseTD.callCounts, 1)
    }

    @Test
    fun `onReloadClick show loading indicator`() = runTest {
        SUT.onReloadClick()
        verify(homeMvcViewMock).showCurrentEventIndicator()
    }

    @Test
    fun `onReloadClick hide not found container`() = runTest {
        SUT.onReloadClick()
        verify(homeMvcViewMock).hideCurrentEventEmpty()
    }

    @Test
    fun `onReloadClick getCurrentEvent finished call hideCurrentEventIndicator`() = runTest {
        SUT.onReloadClick()
        advanceUntilIdle()
        verify(homeMvcViewMock, times(1)).hideCurrentEventIndicator()
    }

    @Test
    fun `onReloadClick getCurrentEvent success call view to display data`() = runTest {
        SUT.onReloadClick()
        verify(homeMvcViewMock).bindCurrentEvent(EventProvider.provideEvent())
        verify(homeMvcViewMock).showCurrentEvent()
    }

    @Test
    fun `onReloadClick getCurrentEvent empty call display empty data`() = runTest{
        getCurrentUseCaseTD.empty = true
        SUT.onReloadClick()
        verify(homeMvcViewMock).showCurrentEventEmpty()
    }

    @Test
    fun `onReloadClick getCurrentEvent generic error call display empty data and toast`() = runTest{
        getCurrentUseCaseTD.genericError = true
        SUT.onReloadClick()
        verify(homeMvcViewMock).showCurrentEventEmpty()
        verify(toastHelperMock).showGenericError()
    }

    @Test
    fun `onReloadClick getCurrentEvent network error call display empty data and toast`() = runTest{
        getCurrentUseCaseTD.networkError = true
        SUT.onReloadClick()
        verify(homeMvcViewMock).showCurrentEventEmpty()
        verify(toastHelperMock).showNetworkError()
    }

    @Test
    fun `onReloadClick hide current event`() = runTest{
        SUT.onReloadClick()
        verify(homeMvcViewMock).hideCurrentEvent()
    }

    @Test
    fun `onStart hide current event`() = runTest{
        SUT.onStart()
        verify(homeMvcViewMock).hideCurrentEvent()
    }

    @Test
    fun `onOpenClick should open url`(){
        SUT.onOpenClick("https://youtube.com")
        verify(intentHelperMock).openUrl("https://youtube.com")
    }


    @Test
    fun `onStart should get upcoming events`(){
        SUT.onStart()
        assertEquals(getUpcomingEventsTD.callCounts, 1)
    }

    @Test
    fun `onStart should hide upcoming eventlist`(){
        SUT.onStart()
        verify(homeMvcViewMock).hideUpcomingEventList()
    }

    @Test
    fun `onStart should display upcoming loadingIndicator`(){
        SUT.onStart()
        verify(homeMvcViewMock).showUpcomingEventLoadingIndicator()
    }


    @Test
    fun `onStart should hide upcoming no data`(){
        SUT.onStart()
        verify(homeMvcViewMock).hideUpcomingNoData()
    }

    @Test
    fun `getUpcomingEvents success should bind events`(){
        SUT.onStart()
        verify(homeMvcViewMock).bindUpcomingEvents(EventProvider.provideEventList())
    }

    @Test
    fun `getUpcomingEvents success should display events`(){
        SUT.onStart()
        verify(homeMvcViewMock).showUpcomingEventList()
    }

    @Test
    fun `getUpcomingEvents empty should display no events`(){
        getUpcomingEventsTD.empty = true
        SUT.onStart()
        verify(homeMvcViewMock).showUpcomingEventNoData()
    }

    @Test
    fun `getUpcomingEvents general error should display no events`(){
        getUpcomingEventsTD.generalError = true
        SUT.onStart()
        verify(homeMvcViewMock).showUpcomingEventNoData()
    }

    @Test
    fun `getUpcomingEvents network error should display no events`(){
        getUpcomingEventsTD.networkError = true
        SUT.onStart()
        verify(homeMvcViewMock).showUpcomingEventNoData()
    }

    @Test
    fun `getUpcomingEvents general error should display toast`(){
        getUpcomingEventsTD.generalError = true
        SUT.onStart()
        verify(toastHelperMock).showGenericError()
    }

    @Test
    fun `getUpcomingEvents network error should display toast`(){
        getUpcomingEventsTD.networkError = true
        SUT.onStart()
        verify(toastHelperMock).showNetworkError()
    }

    @Test
    fun `getUpcomingEvents finished should hide loading indicator`() = runTest{
        SUT.onStart()
        advanceUntilIdle()
        verify(homeMvcViewMock).hideUpcomingEventLoadingIndicator()
    }

    @Test
    fun `onUpcomingClick should navigate to the detail`(){
        SUT.onUpcomingClick("1")
        verify(screensNavigatorMock).goToEventDetail("1")
    }


    class  GetCurrentUseCaseTD: GetCurrentEventUseCase{
        var callCounts = 0
        var empty = false
        var genericError = false
        var networkError = false

        override suspend fun getCurrent(): CurrentEventResponse {
            callCounts += 1
            if (empty){
                return CurrentEventResponse.EmptyEvent
            }
            if (genericError) return CurrentEventResponse.GeneralError
            if (networkError) return CurrentEventResponse.NetworkError

            return CurrentEventResponse.Success(
                EventProvider.provideEvent()
            )
        }
    }


    class GetUpcomingEventUseCaseTD: GetUpcomingEventsUseCase{
        var empty = false
        var generalError = false
        var networkError = false
        var callCounts = 0
        override suspend fun getUpcomingEvents(): UpcomingEventsResponse {
            callCounts += 1
            if (empty){
                return UpcomingEventsResponse.EmptyEvent
            }
            if (generalError){
                return UpcomingEventsResponse.GeneralError
            }
            if (networkError)
                return UpcomingEventsResponse.NetworkError
            return UpcomingEventsResponse.Success(EventProvider.provideEventList())
        }

    }

}
