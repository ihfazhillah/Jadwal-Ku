package com.ihfazh.jadwal_ku.authentication


interface LoginUseCase {
    sealed interface Result {
        object NetworkError: Result
        object LoginError: Result
        object GeneralError: Result
        data class Success(val token: String): Result
    }

    suspend fun login(username: String, password: String): Result
}