package com.ihfazh.jadwal_ku.screens.login

import com.ihfazh.jadwal_ku.authentication.AuthenticationStateManager
import com.ihfazh.jadwal_ku.authentication.LoginData
import com.ihfazh.jadwal_ku.authentication.LoginUseCase
import com.ihfazh.jadwal_ku.screens.common.StringHelper
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

private const val FIELD_CANNOT_BE_EMPTY = "Field ini tidak boleh kosong"

private const val USERNAME = "username"

private const val PASSWORD = "somepassword"

private const val GLOBAL_ERROR = "Global Error"

private const val LOGIN_ERROR = "login error"

private const val GENERAL_ERROR = "general error"

private const val TOKEN = "token"

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LoginViewControllerTest {

    lateinit var SUT: LoginViewController
    lateinit var loginUseCaseTD: LoginUseCaseTD

    @Mock lateinit var viewMvc: LoginViewMvc
    @Mock lateinit var stringHelperMock: StringHelper
    @Mock lateinit var authenticationStateManagerMock: AuthenticationStateManager
    @Mock lateinit var navigatorMock: ScreensNavigator

    @Before
    fun setUp() {
        loginUseCaseTD = LoginUseCaseTD()

        SUT = LoginViewController(
            loginUseCaseTD,
            authenticationStateManagerMock,
            navigatorMock,
            stringHelperMock,
            UnconfinedTestDispatcher()
        )
        SUT.bindView(viewMvc)
        `when`(viewMvc.getLoginData()).thenReturn(LoginData(USERNAME, PASSWORD))
    }

    @Test
    fun `onStart register view listeners`(){
        SUT.onStart()
        verify(viewMvc).registerListener(SUT)
    }

    @Test
    fun `onStop unregister view listeners`(){
        SUT.onStop()
        verify(viewMvc).unregisterListener(SUT)
    }

    @Test
    fun `onLoginClick should call getFormData from view`(){
        SUT.onLoginClick()
        verify(viewMvc).getLoginData()
    }

    @Test
    fun `onLoginClick getLoginData username empty set username error`(){
        `when`(viewMvc.getLoginData()).thenReturn(LoginData("", PASSWORD))
        `when`(stringHelperMock.getUsernameRequiredErrorString()).thenReturn(FIELD_CANNOT_BE_EMPTY)

        SUT.onLoginClick()

        verify(viewMvc).setUsernameError(FIELD_CANNOT_BE_EMPTY)
    }

    @Test
    fun `onLoginClick getLoginData password empty set username error`(){
        `when`(viewMvc.getLoginData()).thenReturn(LoginData("someusername", ""))
        `when`(stringHelperMock.getPasswordRequiredErrorString()).thenReturn(FIELD_CANNOT_BE_EMPTY)

        SUT.onLoginClick()

        verify(viewMvc).setPasswordError(FIELD_CANNOT_BE_EMPTY)
    }

    @Test
    fun `onLoginClick should remove all errors initially`(){
        SUT.onLoginClick()
        verify(viewMvc).hideGlobalError()
        verify(viewMvc).hideUsernameError()
        verify(viewMvc).hidePasswordError()
    }

    @Test
    fun `onLoginClick data not valid dont call login usecase`(){
        `when`(viewMvc.getLoginData()).thenReturn(LoginData("someusername", ""))
        SUT.onLoginClick()
        assertEquals(loginUseCaseTD.callsCount, 0)
    }

    @Test
    fun `onLoginClick should disable login button`() = runTest {
        SUT.onLoginClick()
        verify(viewMvc).disableLoginButton()
    }

    @Test
    fun `onLoginClick should enable login button after network call finished`() = runTest {
        SUT.onLoginClick()
        advanceUntilIdle()
        verify(viewMvc).enableLoginButton()
    }

    @Test
    fun `onLoginClick valid should send the correct parameter`(){
        SUT.onLoginClick()
        loginUseCaseTD.assertCalledWith(USERNAME, PASSWORD)
    }

    @Test
    fun `onLoginClick network error`() = runTest {
        loginUseCaseTD.isNetworkError = true
        `when`(stringHelperMock.getGlobalErrorString()).thenReturn(GLOBAL_ERROR)
        SUT.onLoginClick()
        advanceUntilIdle()
        verify(viewMvc).setAndDisplayGlobalError(GLOBAL_ERROR)
    }

    @Test
    fun `onLoginClick login error`() = runTest {
        loginUseCaseTD.isLoginError = true
        `when`(stringHelperMock.getLoginErrorString()).thenReturn(LOGIN_ERROR)
        SUT.onLoginClick()
        advanceUntilIdle()
        verify(viewMvc).setAndDisplayGlobalError(LOGIN_ERROR)
    }

    @Test
    fun `onLoginClick general error`() = runTest {
        loginUseCaseTD.isGeneralError = true
        `when`(stringHelperMock.getGeneralErrorString()).thenReturn(GENERAL_ERROR)
        SUT.onLoginClick()
        advanceUntilIdle()
        verify(viewMvc).setAndDisplayGlobalError(GENERAL_ERROR)
    }


    @Test
    fun `onLoginClick success should set the token`() = runTest{
        SUT.onLoginClick()
        advanceUntilIdle()
        verify(authenticationStateManagerMock).userLoggedIn(TOKEN)
    }

    @Test
    fun `onLoginSuccess should redirect to home view`() = runTest{
        SUT.onLoginClick()
        advanceUntilIdle()
        verify(navigatorMock).goToHome()
    }
}


class LoginUseCaseTD: LoginUseCase{
    var callsCount = 0
    var username: String? = null
    var password: String? = null
    var isNetworkError = false
    var isLoginError = false
    var isGeneralError = false

    override suspend fun login(username: String, password: String): LoginUseCase.Result {
        callsCount += 1
        this.username = username
        this.password = password

        if (isNetworkError){
            return LoginUseCase.Result.NetworkError
        }

        if (isGeneralError){
            return LoginUseCase.Result.GeneralError
        }

        if (isLoginError){
            return LoginUseCase.Result.LoginError
        }

        return LoginUseCase.Result.Success(TOKEN)

    }

    fun assertCalledWith(username: String, password: String) {
        assertEquals(username, this.username)
        assertEquals(password, this.password)
    }

}