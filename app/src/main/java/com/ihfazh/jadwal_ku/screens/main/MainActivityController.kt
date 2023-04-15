package com.ihfazh.jadwal_ku.screens.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.ihfazh.jadwal_ku.authentication.AuthenticationStateManager
import com.ihfazh.jadwal_ku.dependencyinjection.MainDispatcher
import com.ihfazh.jadwal_ku.screens.common.fragmentframehelper.FragmentChangeHelper
import com.ihfazh.jadwal_ku.screens.common.fragmentframehelper.FragmentFrameHelper
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreenKey
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import com.ihfazh.jadwal_ku.screens.eventlist.EventListFragment
import com.ihfazh.jadwal_ku.screens.home.HomeFragment
import com.ihfazh.jadwal_ku.screens.settings.SettingsFragment
import kotlinx.coroutines.*
import javax.inject.Inject

class MainActivityController @Inject constructor(
    private val screensNavigator: ScreensNavigator,
    private val authenticationStateManager: AuthenticationStateManager,
    private val fragmentChangeHelper: FragmentChangeHelper,
    @MainDispatcher dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : MainViewMvc.Listener, FragmentChangeHelper.Listener {

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
        viewMvc.registerListener(this)
        fragmentChangeHelper.registerListener(this)
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
        viewMvc.unregisterListener(this)
        fragmentChangeHelper.unregisterListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }

    override fun onBottomMenuChanged(screenKey: ScreenKey) {
        when(screenKey){
            ScreenKey.HOME -> screensNavigator.goToHomeAndAddToBackstacks()
            ScreenKey.EVENTS -> screensNavigator.goToEvents()
            ScreenKey.SETTINGS -> screensNavigator.goToSettings()
            else -> throw java.lang.RuntimeException("Not registered to bottom menu")
        }
    }


    override fun onFragmentChanged(fragment: Fragment) {
        val withBottomNav = listOf(
            HomeFragment::class.java,
            SettingsFragment::class.java,
            EventListFragment::class.java
        )

        var shouldHide = true
        withBottomNav.forEach { klass ->
            if (fragment::class.java == klass){
                viewMvc.showBottomNav()
                shouldHide = false
                return@forEach
            }
        }

        if (shouldHide) viewMvc.hideBottomNav()
    }


}