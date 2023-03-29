package com.ihfazh.jadwal_ku.dependencyinjection.presentation

import com.ihfazh.jadwal_ku.event.*
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {
    @Provides
    fun getCurrentEventUseCase(): GetCurrentEventUseCase {
        return InMemoryGetCurrentEventUseCase()
    }

    @Provides
    fun getUpcomingEventsUseCase(): GetUpcomingEventsUseCase{
        return InMemoryGetUpcomingEventsUseCase()
    }

    @Provides
    fun getEventDetailUseCase(): GetEventDetailUseCase = InMemoryGetEventDetailUseCase()
}