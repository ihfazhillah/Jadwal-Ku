package com.ihfazh.jadwal_ku.event

import javax.inject.Inject

class InMemoryGetCurrentEventUseCase @Inject constructor(): GetCurrentEventUseCase {
    private var count = 0

    override suspend fun getCurrent(): CurrentEventResponse {
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