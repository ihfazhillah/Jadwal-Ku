package com.ihfazh.jadwal_ku.screens.login

import com.ihfazh.jadwal_ku.authentication.LoginData
import com.ihfazh.jadwal_ku.screens.common.StringHelper
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

private const val FIELD_CANNOT_BE_EMPTY = "Field ini tidak boleh kosong"

@RunWith(MockitoJUnitRunner::class)
class LoginViewControllerTest {

    lateinit var SUT: LoginViewController
    @Mock lateinit var viewMvc: LoginViewMvc
    @Mock lateinit var stringHelperMock: StringHelper

    @Before
    fun setUp() {
        SUT = LoginViewController(
            stringHelperMock
        )
        SUT.bindView(viewMvc)
        `when`(viewMvc.getLoginData()).thenReturn(LoginData("", "somepassword"))
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

    //  username empty validation
    @Test
    fun `onLoginClick getLoginData username empty set username error`(){
        `when`(viewMvc.getLoginData()).thenReturn(LoginData("", "somepassword"))
        `when`(stringHelperMock.getUsernameRequiredErrorString()).thenReturn(FIELD_CANNOT_BE_EMPTY)

        SUT.onLoginClick()

        verify(viewMvc).setUsernameError(FIELD_CANNOT_BE_EMPTY)
    }
    //  password empty validation
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
    //  parameter should set into usecase
    //  handle network error
    //  handle login error
    //  handle another error
    //  on success login set token
    //  on success login go to home view


}