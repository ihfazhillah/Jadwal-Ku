package com.ihfazh.jadwal_ku.screens.thumbnailview

import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ThumbnailViewControllerTest {
    lateinit var SUT: ThumbnailViewController

    @Mock lateinit var viewMvcMock: ThumbnailViewMvc
    @Mock lateinit var navigatorMock: ScreensNavigator

    @Before
    fun setUp() {
        SUT = ThumbnailViewController(navigatorMock)
        SUT.bindViewMvc(viewMvcMock)
    }

    @Test
    fun onStartRegisterListener(){
        SUT.onStart()
        verify(viewMvcMock).registerListener(SUT)
    }

    @Test
    fun onStopUnRegisterListener(){
        SUT.onStop()
        verify(viewMvcMock).unregisterListener(SUT)
    }

    @Test
    fun onBackPressedNavigateUp(){
        SUT.onBackPressed()
        verify(navigatorMock).navigateUp()
    }

    @Test
    fun bindImageShouldCallViewBindImage(){
        SUT.bindImage("someurl")
        verify(viewMvcMock).bindImage("someurl")
    }

}