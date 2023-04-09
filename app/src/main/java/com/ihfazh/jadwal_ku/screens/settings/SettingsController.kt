package com.ihfazh.jadwal_ku.screens.settings

import com.ihfazh.jadwal_ku.BuildConfig
import com.ihfazh.jadwal_ku.authentication.AuthenticationStateManager
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import javax.inject.Inject

class SettingsController @Inject constructor(
    private val authenticationStateManager: AuthenticationStateManager,
    private val screensNavigator: ScreensNavigator
): SettingsViewMvc.Listener {
    private lateinit var viewMvc: SettingsViewMvc

    fun bindView(view: SettingsViewMvc){
        viewMvc = view
    }

    fun onStart(){
        viewMvc.registerListener(this)
        viewMvc.setVersion(BuildConfig.VERSION_NAME)
    }

    fun onStop(){
        viewMvc.unregisterListener(this)
    }

    override fun onLogoutClicked() {
        authenticationStateManager.logout()
        screensNavigator.goToLogin()
    }
}