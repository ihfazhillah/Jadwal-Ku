package com.ihfazh.jadwal_ku.event.usecases.upcoming

import com.ihfazh.jadwal_ku.event.EventListItem


interface GetUpcomingEventsUseCase {
    sealed class UpcomingEventsResponse {
        data class Success(val events: List<EventListItem>): UpcomingEventsResponse()
        object EmptyEvent: UpcomingEventsResponse()
        object NetworkError: UpcomingEventsResponse()
        object GeneralError: UpcomingEventsResponse()
    }
    suspend fun getUpcomingEvents(): UpcomingEventsResponse
}
