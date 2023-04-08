package com.ihfazh.jadwal_ku.authentication

import com.ihfazh.jadwal_ku.networking.KsatriaMuslimService
import com.ihfazh.jadwal_ku.networking.schemas.LoginBodySchema
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class LoginUseCaseImpl(
    private val remote: KsatriaMuslimService
) : LoginUseCase {
    override suspend fun login(username: String, password: String): LoginUseCase.Result {
        return withContext(Dispatchers.IO){
            try {
                val response = remote.login(LoginBodySchema(username, password))
                return@withContext LoginUseCase.Result.Success(response.token)
            } catch (e: HttpException){
                if (e.code() == 400){
                    return@withContext LoginUseCase.Result.LoginError
                }
            } catch (e: IOException){
                return@withContext LoginUseCase.Result.NetworkError
            }

            return@withContext LoginUseCase.Result.GeneralError
        }
    }
}