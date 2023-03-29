package com.ihfazh.jadwal_ku.event

import kotlinx.coroutines.delay
import javax.inject.Inject

class InMemoryGetEventDetailUseCase @Inject constructor(): GetEventDetailUseCase {
    override suspend fun getDetail(id: String): GetEventDetailUseCase.Result {
        delay(1000)
        return GetEventDetailUseCase.Result.Success(EventProvider.provideEvent())
    }
}