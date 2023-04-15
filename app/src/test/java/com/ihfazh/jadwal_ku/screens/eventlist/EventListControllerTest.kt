package com.ihfazh.jadwal_ku.screens.eventlist

import com.ihfazh.jadwal_ku.event.EventProvider
import com.ihfazh.jadwal_ku.event.usecases.testdoubles.GetUpcomingEventUseCaseTD
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

private const val PAGE_LIMIT = 5

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class EventListControllerTest {

    private lateinit var SUT: EventListController
    private lateinit var getUpcomingEventsTD: GetUpcomingEventUseCaseTD
    private lateinit var eventListStateManager: EventListStateManager

    @Mock lateinit var viewMvcMock: EventListViewMvc
    @Mock lateinit var screensNavigatorMock: ScreensNavigator

    @Before
    fun setUp() {
        getUpcomingEventsTD = GetUpcomingEventUseCaseTD()
        eventListStateManager = EventListStateManager()

        SUT = EventListController(
            getUpcomingEventsTD,
            screensNavigatorMock,
            eventListStateManager,
            UnconfinedTestDispatcher()
        )
        SUT.bindView(viewMvcMock)
    }

    @Test
    fun `onStart register listeners`(){
        SUT.onStart()
        verify(viewMvcMock).registerListener(SUT)
    }

    @Test fun `onStop unregister listeners`(){
        SUT.onStop()
        verify(viewMvcMock).unregisterListener(SUT)
    }

    // on start display loading
    @Test
    fun `onStart display loading`(){
        SUT.onStart()
        verify(viewMvcMock).showLoadingIndicator()
    }

    @Test
    fun `onStart fetch events`(){
        SUT.onStart()
        assertEquals(getUpcomingEventsTD.callCounts, 1)
        assertEquals(getUpcomingEventsTD.page, 1)
        assertEquals(getUpcomingEventsTD.limit, PAGE_LIMIT)
    }
    // on start fetch the event list
    // on event list load finished success hide loading
    @Test
    fun `on loading events done, hide loading`() = runTest {
        SUT.onStart()
        advanceUntilIdle()
        verify(viewMvcMock).hideLoadingIndicator()
    }
    // on event list load finished add events
    @Test
    fun `on loading events done, success bind events`() = runTest {
        SUT.onStart()
        advanceUntilIdle()
        verify(viewMvcMock).bindEvents(EventProvider.provideEventList())
    }

    @Test
    fun `onEventClick navigate to event detail`() {
        SUT.onEventClick("abc")
        verify(screensNavigatorMock).goToEventDetail("abc")
    }

    @Test
    fun `onLastEventItemReached increase page`() = runTest{
        SUT.onStart()
        SUT.onLastEventItemReached()
        assertEquals(getUpcomingEventsTD.page, 2)
        assertEquals(getUpcomingEventsTD.callCounts, 2)
    }

    @Test
    fun `onLastEventItemReached dont trigger if fetching state still true`() = runTest{
        SUT.onStart()
        eventListStateManager.loading = true

        SUT.onLastEventItemReached()
        assertEquals(getUpcomingEventsTD.page, 1)
        assertEquals(getUpcomingEventsTD.callCounts, 1)

        eventListStateManager.loading = false
        SUT.onLastEventItemReached()
        assertEquals(getUpcomingEventsTD.page, 2)
        assertEquals(getUpcomingEventsTD.callCounts, 2)
    }

    @Test
    fun `onLastEventItemReached dont trigger if no next`() = runTest {
        SUT.onStart()
        getUpcomingEventsTD.hasNext = false
        SUT.onLastEventItemReached()
        SUT.onLastEventItemReached()
        assertEquals(getUpcomingEventsTD.page, 2)
        assertEquals(getUpcomingEventsTD.callCounts, 2)
    }

    @Test
    fun `second onStart after onLastEventItemReached should starts from page one`() = runTest {
        SUT.onStart()
        SUT.onLastEventItemReached()
        SUT.onStop()

        SUT.onStart()
        assertEquals(getUpcomingEventsTD.page, 1)
    }

    /*
    TODOS:
    - handle error to display loading dialog
    - on error display appropriate error message in the main list
    - on empty list display appropriate message and retry button
     */

}