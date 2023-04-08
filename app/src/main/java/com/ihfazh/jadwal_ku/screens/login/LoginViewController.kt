package com.ihfazh.jadwal_ku.screens.login

import com.ihfazh.jadwal_ku.authentication.AuthenticationStateManager
import com.ihfazh.jadwal_ku.authentication.LoginUseCase
import com.ihfazh.jadwal_ku.dependencyinjection.MainDispatcher
import com.ihfazh.jadwal_ku.screens.common.StringHelper
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import kotlinx.coroutines.*
import javax.inject.Inject

class LoginViewController @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val authenticationStateManager: AuthenticationStateManager,
    private val screensNavigator: ScreensNavigator,
    private val stringHelper: StringHelper,
    @MainDispatcher val dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : LoginViewMvc.Listener {
    private lateinit var viewMvc: LoginViewMvc
    private val coroutineScope = CoroutineScope(dispatcher)

    fun bindView(viewMvc: LoginViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart(){
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }

    override fun onLoginClick() {
        viewMvc.hideUsernameError()
        viewMvc.hidePasswordError()
        viewMvc.hideGlobalError()

        val loginData = viewMvc.getLoginData()

        var valid = true

        if (loginData.username.isEmpty()){
            viewMvc.setUsernameError(stringHelper.getUsernameRequiredErrorString())
            valid = false
        }

        if (loginData.password.isEmpty()){
            viewMvc.setPasswordError(stringHelper.getPasswordRequiredErrorString())
            valid = false
        }

        if (!valid) return

        viewMvc.disableLoginButton()

        coroutineScope.launch {
            val result = loginUseCase.login(username = loginData.username, password = loginData.password)
            when(result){
                LoginUseCase.Result.GeneralError -> viewMvc.setAndDisplayGlobalError(stringHelper.getGeneralErrorString())
                LoginUseCase.Result.LoginError -> viewMvc.setAndDisplayGlobalError(stringHelper.getLoginErrorString())
                LoginUseCase.Result.NetworkError -> viewMvc.setAndDisplayGlobalError(stringHelper.getGlobalErrorString())
                is LoginUseCase.Result.Success -> {
                    authenticationStateManager.userLoggedIn(result.token)
                    screensNavigator.goToHome()
                }
            }

            viewMvc.enableLoginButton()
        }

    }
}