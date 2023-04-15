package com.ihfazh.jadwal_ku.event.usecases.upcoming

import com.ihfazh.jadwal_ku.event.EventProvider
import com.ihfazh.jadwal_ku.event.usecases.upcoming.GetUpcomingEventsUseCase.*
import kotlinx.coroutines.delay
import javax.inject.Inject

class InMemoryGetUpcomingEventsUseCase @Inject constructor(): GetUpcomingEventsUseCase {
    private var count = 0

    override suspend fun getUpcomingEvents(limit: Int, page: Int): UpcomingEventsResponse {
        delay(1000)
        return UpcomingEventsResponse.Success(EventProvider.provideEventList())
    }
}