package com.ihfazh.jadwal_ku.event.usecases.detail

import com.ihfazh.jadwal_ku.event.Event


interface GetEventDetailUseCase {
    sealed class Result {
        data class Success(val event: Event): Result()
        object NetworkError: Result()
        object GeneralError: Result()
    }

    suspend fun getDetail(id: String): Result
}
