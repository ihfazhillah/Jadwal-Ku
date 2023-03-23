package com.ihfazh.jadwal_ku.screens.home

import com.ihfazh.jadwal_ku.event.CurrentEventResponse
import com.ihfazh.jadwal_ku.event.EventProvider
import com.ihfazh.jadwal_ku.event.GetCurrentEventUseCase
import com.ihfazh.jadwal_ku.screens.common.ToastHelper
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
    private lateinit var dispatcher: TestDispatcher

    @Mock private lateinit var homeMvcViewMock: HomeMvcView
    @Mock private lateinit var toastHelperMock: ToastHelper

    @Before
    fun setUp(){
        getCurrentUseCaseTD = GetCurrentUseCaseTD()
        dispatcher = UnconfinedTestDispatcher()

        SUT = HomeController(
            getCurrentUseCaseTD,
            toastHelperMock,
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

}
