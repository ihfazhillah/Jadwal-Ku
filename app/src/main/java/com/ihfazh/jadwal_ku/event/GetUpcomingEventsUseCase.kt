package com.ihfazh.jadwal_ku.event


interface GetUpcomingEventsUseCase {
    suspend fun getUpcomingEvents(): UpcomingEventsResponse
}