package com.ihfazh.jadwal_ku.screens.settings

import com.ihfazh.jadwal_ku.BuildConfig
import com.ihfazh.jadwal_ku.authentication.AuthenticationStateManager
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SettingsControllerTest {
    lateinit var SUT: SettingsController

    @Mock lateinit var viewMvcMock: SettingsViewMvc
    @Mock lateinit var  authenticationStateManagerMock: AuthenticationStateManager
    @Mock lateinit var screensNavigatorMock: ScreensNavigator

    @Before
    fun setUp() {
        SUT = SettingsController(authenticationStateManagerMock, screensNavigatorMock)
        SUT.bindView(viewMvcMock)
    }

    @Test
    fun `onStart should register listener`(){
        SUT.onStart()
        verify(viewMvcMock).registerListener(SUT)
    }

    @Test
    fun `onStart should set version`(){
        SUT.onStart()
        verify(viewMvcMock).setVersion(BuildConfig.VERSION_NAME)
    }

    @Test
    fun `onStop should unregister listener`(){
        SUT.onStop()
        verify(viewMvcMock).unregisterListener(SUT)
    }

    @Test
    fun `onLogoutClicked should logout user`(){
        SUT.onLogoutClicked()
        verify(authenticationStateManagerMock).logout()
    }

    @Test
    fun `onLogoutClicked should redirect to login`(){
        SUT.onLogoutClicked()
        verify(screensNavigatorMock).goToLogin()
    }
}