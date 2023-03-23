package com.ihfazh.jadwal_ku.event


interface GetCurrentEventUseCase {
    suspend fun getCurrent(): CurrentEventResponse
}