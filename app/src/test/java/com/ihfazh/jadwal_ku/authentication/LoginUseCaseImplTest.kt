package com.ihfazh.jadwal_ku.authentication

import com.ihfazh.jadwal_ku.networking.KsatriaMuslimService
import com.ihfazh.jadwal_ku.networking.schemas.LoginBodySchema
import com.ihfazh.jadwal_ku.networking.schemas.LoginResponseSchema
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

private const val PASSWORD = "password"

private const val USERNAME = "username"

private const val TOKEN = "token"

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LoginUseCaseImplTest {
    lateinit var SUT : LoginUseCase

    @Mock lateinit var remoteMock: KsatriaMuslimService

    @Before
    fun setUp() {
        SUT = LoginUseCaseImpl(remoteMock)
    }

    @Test
    fun `login send correct parameter`() = runTest{
        `when`(remoteMock.login(LoginBodySchema(USERNAME, PASSWORD))).thenReturn(
            LoginResponseSchema(TOKEN)
        )
        SUT.login(USERNAME, PASSWORD)
        verify(remoteMock).login(LoginBodySchema(USERNAME, PASSWORD))
    }

    @Test
    fun `login happy path send correct token`() = runTest{
        `when`(remoteMock.login(LoginBodySchema(USERNAME, PASSWORD))).thenReturn(
            LoginResponseSchema(TOKEN)
        )
        val resp = SUT.login(USERNAME, PASSWORD) as LoginUseCase.Result.Success
        advanceUntilIdle()
        assertEquals(resp.token, TOKEN)
    }

    @Test
    fun `login login error returns login error`() = runTest {
        `when`(remoteMock.login(LoginBodySchema(USERNAME, PASSWORD))).thenAnswer{
            throw HttpException(Response.error<LoginResponseSchema>(400, mock()))
        }
        val resp = SUT.login(USERNAME, PASSWORD)
        advanceUntilIdle()
        assertEquals(resp, LoginUseCase.Result.LoginError)
    }

    @Test
    fun `login another error returns general error`() = runTest {
        `when`(remoteMock.login(LoginBodySchema(USERNAME, PASSWORD))).thenAnswer{
            throw HttpException(Response.error<LoginResponseSchema>(500, mock()))
        }
        val resp = SUT.login(USERNAME, PASSWORD)
        advanceUntilIdle()
        assertEquals(resp, LoginUseCase.Result.GeneralError)
    }

    @Test
    fun `login network error returns network error`() = runTest {
        `when`(remoteMock.login(LoginBodySchema(USERNAME, PASSWORD))).thenAnswer{
            throw IOException()
        }
        val resp = SUT.login(USERNAME, PASSWORD)
        advanceUntilIdle()
        assertEquals(resp, LoginUseCase.Result.NetworkError)
    }
}