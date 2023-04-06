package com.ihfazh.jadwal_ku.screens.main

import android.os.Bundle
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import javax.inject.Inject

class MainActivityController @Inject constructor(
    private val screensNavigator: ScreensNavigator
) {

    lateinit var viewMvc: MainViewMvc
    var savedInstanceState: Bundle? = null

    fun bindView(viewMvc: MainViewMvc){
        this.viewMvc = viewMvc
    }

    fun bindSavedInstanceState(savedInstanceState: Bundle?){
        this.savedInstanceState = savedInstanceState
    }

    fun onStart(){
        if (savedInstanceState == null){
            screensNavigator.goToHome()
        }
    }

    fun onStop(){

    }


}