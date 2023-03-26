package com.ihfazh.jadwal_ku.event


interface GetCurrentEventUseCase {
    sealed class CurrentEventResponse {
        data class Success(val event: Event): CurrentEventResponse()
        object EmptyEvent: CurrentEventResponse()
        object NetworkError: CurrentEventResponse()
        object GeneralError: CurrentEventResponse()
    }

    suspend fun getCurrent(): CurrentEventResponse
}
