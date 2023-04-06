package com.ihfazh.jadwal_ku.screens.main

import com.ihfazh.jadwal_ku.authentication.AuthenticationStateManager
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainActivityControllerTest {

    lateinit var SUT: MainActivityController
    lateinit var dispatcher: CoroutineDispatcher

    @Mock lateinit var screensNavigatorMock: ScreensNavigator
    @Mock lateinit var viewMvc: MainViewMvc
    @Mock lateinit var authenticationStateManagerMock: AuthenticationStateManager

    @Before
    fun setUp() {
        dispatcher = UnconfinedTestDispatcher()

        SUT = MainActivityController(
            screensNavigatorMock,
            authenticationStateManagerMock,
            dispatcher
        )
        SUT.bindView(viewMvc)
    }

    @Test
    fun `onStart logged in goToHome called`() = runTest{
        `when`(authenticationStateManagerMock.isLoggedIn()).thenReturn(true)
        SUT.onStart()
        verify(screensNavigatorMock).goToHome()
    }

    @Test
    fun `onStart loggedIn false go to login fragment called`() = runTest{
        `when`(authenticationStateManagerMock.isLoggedIn()).thenReturn(false)
        SUT.onStart()
        verify(screensNavigatorMock).goToLogin()

    }


}