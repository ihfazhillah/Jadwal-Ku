package com.ihfazh.jadwal_ku.screens.common

import android.content.Context
import javax.inject.Inject


class SettingsHelper @Inject constructor(
    context: Context
) {

    private val sharedPreferences = context.getSharedPreferences(SettingsKey, Context.MODE_PRIVATE)

    fun getToken(): String? = sharedPreferences.getString(USER_TOKEN, "")
    fun setToken(token: String) {
        sharedPreferences.edit().apply {
            putString(USER_TOKEN, token)
            apply()
        }
    }

    companion object {
        private const val SettingsKey = "global_settings"
        private const val USER_TOKEN = "user_token"
    }
}