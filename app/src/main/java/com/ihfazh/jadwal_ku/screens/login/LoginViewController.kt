package com.ihfazh.jadwal_ku.screens.login

import com.ihfazh.jadwal_ku.screens.common.StringHelper
import kotlin.math.log

class LoginViewController(
    private val stringHelper: StringHelper
) : LoginViewMvc.Listener {
    private lateinit var viewMvc: LoginViewMvc

    fun bindView(viewMvc: LoginViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart(){
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    override fun onLoginClick() {
        viewMvc.hideUsernameError()
        viewMvc.hidePasswordError()
        viewMvc.hideGlobalError()

        val loginData = viewMvc.getLoginData()

        if (loginData.username.isEmpty()){
            viewMvc.setUsernameError(stringHelper.getUsernameRequiredErrorString())
        }

        if (loginData.password.isEmpty()){
            viewMvc.setPasswordError(stringHelper.getPasswordRequiredErrorString())
        }
    }
}