package com.ihfazh.jadwal_ku.event.usecases.current

import com.ihfazh.jadwal_ku.event.EventProvider
import com.ihfazh.jadwal_ku.event.usecases.current.GetCurrentEventUseCase.*
import kotlinx.coroutines.delay
import javax.inject.Inject

class InMemoryGetCurrentEventUseCase @Inject constructor(): GetCurrentEventUseCase {
    private var count = 0

    override suspend fun getCurrent(): CurrentEventResponse {
        delay(1000)
        count += 1

        if (count == 1){
            return CurrentEventResponse.EmptyEvent
        }

        if (count == 2){
            return CurrentEventResponse.NetworkError
        }

        if (count == 3){
            return CurrentEventResponse.GeneralError
        }

        count = 0
        return CurrentEventResponse.Success(EventProvider.provideEvent())
    }
}