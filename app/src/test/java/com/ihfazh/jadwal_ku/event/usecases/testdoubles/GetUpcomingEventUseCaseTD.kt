package com.ihfazh.jadwal_ku.event.usecases.testdoubles

import com.ihfazh.jadwal_ku.event.EventProvider
import com.ihfazh.jadwal_ku.event.usecases.upcoming.GetUpcomingEventsUseCase

class GetUpcomingEventUseCaseTD: GetUpcomingEventsUseCase {
    var empty = false
    var generalError = false
    var networkError = false
    var callCounts = 0

    var hasNext = true

    var page : Int? = null
    var limit: Int? = null

    override suspend fun getUpcomingEvents(
        limit: Int,
        page: Int
    ): GetUpcomingEventsUseCase.UpcomingEventsResponse {
        this.page = page
        this.limit = limit

        callCounts += 1
        if (empty){
            return GetUpcomingEventsUseCase.UpcomingEventsResponse.EmptyEvent
        }
        if (generalError){
            return GetUpcomingEventsUseCase.UpcomingEventsResponse.GeneralError
        }
        if (networkError)
            return GetUpcomingEventsUseCase.UpcomingEventsResponse.NetworkError
        return GetUpcomingEventsUseCase.UpcomingEventsResponse.Success(EventProvider.provideEventList(), hasNext)
    }

}