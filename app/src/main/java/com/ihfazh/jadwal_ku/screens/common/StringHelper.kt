package com.ihfazh.jadwal_ku.screens.common

import android.content.Context
import androidx.annotation.StringRes
import com.ihfazh.jadwal_ku.R
import javax.inject.Inject

class StringHelper @Inject constructor(
    private val context: Context
) {

    fun getUsernameRequiredErrorString(): String {
        return getStringById(R.string.username_required_error)
    }

    fun getPasswordRequiredErrorString(): String {
        return getStringById(R.string.password_required_error)
    }

    fun getGlobalErrorString(): String {
        return getStringById(R.string.global_error)
    }

    fun getLoginErrorString(): String {
        return getStringById(R.string.login_error)
    }

    fun getGeneralErrorString(): String {
        return getStringById(R.string.general_error)
    }

    private fun getStringById(@StringRes id: Int): String{
        return context.getString(id)
    }

}