package com.ihfazh.jadwal_ku.dependencyinjection.presentation

import com.ihfazh.jadwal_ku.event.GetCurrentEventUseCase
import com.ihfazh.jadwal_ku.event.GetUpcomingEventsUseCase
import com.ihfazh.jadwal_ku.event.InMemoryGetCurrentEventUseCase
import com.ihfazh.jadwal_ku.event.InMemoryGetUpcomingEventsUseCase
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
}