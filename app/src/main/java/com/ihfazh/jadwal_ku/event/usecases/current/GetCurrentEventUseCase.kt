package com.ihfazh.jadwal_ku.event.usecases.current

import com.ihfazh.jadwal_ku.event.Event


interface GetCurrentEventUseCase {
    sealed class CurrentEventResponse {
        data class Success(val event: Event): CurrentEventResponse()
        object EmptyEvent: CurrentEventResponse()
        object NetworkError: CurrentEventResponse()
        object GeneralError: CurrentEventResponse()
    }

    suspend fun getCurrent(): CurrentEventResponse
}
