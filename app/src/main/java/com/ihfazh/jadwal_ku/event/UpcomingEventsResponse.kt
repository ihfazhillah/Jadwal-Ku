package com.ihfazh.jadwal_ku.event

sealed class UpcomingEventsResponse {
    data class Success(val events: ArrayList<EventListItem>): UpcomingEventsResponse()
    object EmptyEvent: UpcomingEventsResponse()
    object NetworkError: UpcomingEventsResponse()
    object GeneralError: UpcomingEventsResponse()
}