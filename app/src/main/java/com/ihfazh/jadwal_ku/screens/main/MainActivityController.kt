package com.ihfazh.jadwal_ku.screens.main

import android.os.Bundle
import com.ihfazh.jadwal_ku.authentication.AuthenticationStateManager
import com.ihfazh.jadwal_ku.dependencyinjection.MainDispatcher
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import kotlinx.coroutines.*
import javax.inject.Inject

class MainActivityController @Inject constructor(
    private val screensNavigator: ScreensNavigator,
    private val authenticationStateManager: AuthenticationStateManager,
    @MainDispatcher dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) {

    lateinit var viewMvc: MainViewMvc
    private var savedInstanceState: Bundle? = null
    private val coroutineScope = CoroutineScope(dispatcher)

    fun bindView(viewMvc: MainViewMvc){
        this.viewMvc = viewMvc
    }

    fun bindSavedInstanceState(savedInstanceState: Bundle?){
        this.savedInstanceState = savedInstanceState
    }

    fun onStart(){
        coroutineScope.launch {

            if (savedInstanceState == null){
                screensNavigator.goToSplashScreen()

                if (authenticationStateManager.isLoggedIn()){
                    screensNavigator.goToHome()
                } else {
                    screensNavigator.goToLogin()
                }
            }
        }
    }

    fun onStop(){
        coroutineScope.coroutineContext.cancelChildren()
    }


}