package com.ihfazh.jadwal_ku.event

import kotlinx.coroutines.delay
import javax.inject.Inject

class InMemoryGetUpcomingEventsUseCase @Inject constructor(): GetUpcomingEventsUseCase {
    private var count = 0

    override suspend fun getUpcomingEvents(): UpcomingEventsResponse {
        delay(1000)
        return UpcomingEventsResponse.Success(EventProvider.provideEventList())
    }
}