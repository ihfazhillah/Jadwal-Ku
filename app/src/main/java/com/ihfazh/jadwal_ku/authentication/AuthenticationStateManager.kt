package com.ihfazh.jadwal_ku.authentication

import com.ihfazh.jadwal_ku.screens.common.SettingsHelper
import javax.inject.Inject

class AuthenticationStateManager @Inject constructor(
    private val settingsHelper: SettingsHelper
) {

    suspend fun isLoggedIn(): Boolean {
        val token = settingsHelper.getToken()
        return !token.isNullOrEmpty()
    }
    fun userLoggedIn(token: String) {
        settingsHelper.setToken(token)
    }

}