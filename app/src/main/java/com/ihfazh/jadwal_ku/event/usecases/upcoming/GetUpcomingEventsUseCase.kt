package com.ihfazh.jadwal_ku.event.usecases.upcoming

import com.ihfazh.jadwal_ku.event.EventListItem


interface GetUpcomingEventsUseCase {
    sealed class UpcomingEventsResponse {
        data class Success(val events: List<EventListItem>, val hasNext: Boolean = false): UpcomingEventsResponse()
        object EmptyEvent: UpcomingEventsResponse()
        object NetworkError: UpcomingEventsResponse()
        object GeneralError: UpcomingEventsResponse()
    }
    suspend fun getUpcomingEvents(
        limit: Int = 5,
        page: Int = 1
    ): UpcomingEventsResponse
}
