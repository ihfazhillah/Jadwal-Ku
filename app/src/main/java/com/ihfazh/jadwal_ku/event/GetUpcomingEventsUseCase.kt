package com.ihfazh.jadwal_ku.event


interface GetUpcomingEventsUseCase {
    sealed class UpcomingEventsResponse {
        data class Success(val events: ArrayList<EventListItem>): UpcomingEventsResponse()
        object EmptyEvent: UpcomingEventsResponse()
        object NetworkError: UpcomingEventsResponse()
        object GeneralError: UpcomingEventsResponse()
    }
    suspend fun getUpcomingEvents(): UpcomingEventsResponse
}
