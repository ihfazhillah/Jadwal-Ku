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
        TODO("Not yet implemented")
    }

    fun getGlobalErrorString(): String {
        TODO("Not yet implemented")
    }

    fun getLoginErrorString(): String {
        TODO("Not yet implemented")
    }

    fun getGeneralErrorString(): String {
        TODO()
    }

    private fun getStringById(@StringRes id: Int): String{
        return context.getString(id)
    }

}