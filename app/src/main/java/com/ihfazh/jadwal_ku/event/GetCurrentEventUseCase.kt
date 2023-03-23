package com.ihfazh.jadwal_ku.event


sealed class CurrentEventResponse {
    data class Success(val event: Event): CurrentEventResponse()
    object EmptyEvent: CurrentEventResponse()
    object NetworkError: CurrentEventResponse()
    object GeneralError: CurrentEventResponse()
}

interface GetCurrentEventUseCase {
    suspend fun getCurrent(): CurrentEventResponse
}