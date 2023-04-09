package com.ihfazh.jadwal_ku.screens.main

import android.os.Bundle
import com.ihfazh.jadwal_ku.authentication.AuthenticationStateManager
import com.ihfazh.jadwal_ku.screens.common.fragmentframehelper.FragmentChangeHelper
import com.ihfazh.jadwal_ku.screens.common.fragmentframehelper.FragmentFrameHelper
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreenKey
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import com.ihfazh.jadwal_ku.screens.eventdetail.EventDetailFragment
import com.ihfazh.jadwal_ku.screens.home.HomeFragment
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

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainActivityControllerTest {

    lateinit var SUT: MainActivityController
    lateinit var dispatcher: CoroutineDispatcher

    @Mock lateinit var screensNavigatorMock: ScreensNavigator
    @Mock lateinit var viewMvc: MainViewMvc
    @Mock lateinit var authenticationStateManagerMock: AuthenticationStateManager
    @Mock lateinit var fragmentChangeHelperMock: FragmentChangeHelper

    @Before
    fun setUp() {
        dispatcher = UnconfinedTestDispatcher()

        SUT = MainActivityController(
            screensNavigatorMock,
            authenticationStateManagerMock,
            fragmentChangeHelperMock,
            dispatcher
        )
        SUT.bindView(viewMvc)
    }

    @Test
    fun `onStart initially goToSplashScreen called`() = runTest {
        `when`(authenticationStateManagerMock.isLoggedIn()).thenReturn(true)
        SUT.onStart()
        verify(screensNavigatorMock).goToSplashScreen()
    }

    @Test
    fun `onStart logged in goToHome called`() = runTest{
        `when`(authenticationStateManagerMock.isLoggedIn()).thenReturn(true)
        SUT.onStart()
        advanceUntilIdle()
        verify(screensNavigatorMock).goToHome()
    }

    @Test
    fun `onStart loggedIn false go to login fragment called`() = runTest{
        `when`(authenticationStateManagerMock.isLoggedIn()).thenReturn(false)
        SUT.onStart()
        advanceUntilIdle()
        verify(screensNavigatorMock).goToLogin()
    }

    @Test
    fun `onStart savedInstanceState not null don t call navigation`(){
        SUT.bindSavedInstanceState(Bundle())
        SUT.onStart()
        verifyNoInteractions(screensNavigatorMock)
    }

    @Test
    fun `onStart should register listeners`(){
        SUT.onStart()
        verify(viewMvc).registerListener(SUT)
        verify(fragmentChangeHelperMock).registerListener(SUT)
    }

    @Test
    fun `onStop should unregister listeners`(){
        SUT.onStop()
        verify(viewMvc).unregisterListener(SUT)
        verify(fragmentChangeHelperMock).unregisterListener(SUT)
    }

    // onbottommenuchanged home, list, settings

    @Test
    fun `onBottomMenuChanged to home should navigate to home`(){
        SUT.onBottomMenuChanged(ScreenKey.HOME)
        verify(screensNavigatorMock).goToHomeAndAddToBackstacks()
    }

    @Test fun `onBottomMenuChanged to events should go to events`(){
        SUT.onBottomMenuChanged(ScreenKey.EVENTS)
        verify(screensNavigatorMock).goToEvents()
    }

    @Test fun `onBottomMenuChanged to settings should go to settings`(){
        SUT.onBottomMenuChanged(ScreenKey.SETTINGS)
        verify(screensNavigatorMock).goToSettings()
    }

    @Test fun `onFragmentChanged to home display bottom navbar`(){
        SUT.onFragmentChanged(HomeFragment())
        verify(viewMvc).showBottomNav()
    }

    @Test fun `onFragmentChanged to another hide bottom navbar`(){
        SUT.onFragmentChanged(EventDetailFragment())
        verify(viewMvc).hideBottomNav()
    }


}