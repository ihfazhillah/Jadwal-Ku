package com.ihfazh.jadwal_ku.screens.main

import android.os.Bundle
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import javax.inject.Inject

class MainActivityController @Inject constructor(
    private val screensNavigator: ScreensNavigator
) {

    lateinit var viewMvc: MainViewMvc

    fun bindView(viewMvc: MainViewMvc){
        this.viewMvc = viewMvc
    }

    fun onStart(){

    }

    fun onStop(){

    }


    fun initNavigation(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            screensNavigator.goToHome()
        }
    }
}