package com.ihfazh.jadwal_ku.dependencyinjection.presentation

import com.ihfazh.jadwal_ku.common.MediaUrlHelper
import com.ihfazh.jadwal_ku.event.usecases.current.GetCurrentEventUseCase
import com.ihfazh.jadwal_ku.event.usecases.current.GetCurrentEventUseCaseImpl
import com.ihfazh.jadwal_ku.event.usecases.current.InMemoryGetCurrentEventUseCase
import com.ihfazh.jadwal_ku.event.usecases.detail.GetEventDetailUseCase
import com.ihfazh.jadwal_ku.event.usecases.detail.InMemoryGetEventDetailUseCase
import com.ihfazh.jadwal_ku.event.usecases.upcoming.GetUpcomingEventsUseCase
import com.ihfazh.jadwal_ku.event.usecases.upcoming.InMemoryGetUpcomingEventsUseCase
import com.ihfazh.jadwal_ku.networking.KsatriaMuslimService
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {
    @Provides
    fun getCurrentEventUseCase(ksatriaMuslimService: KsatriaMuslimService, mediaUrlHelper: MediaUrlHelper): GetCurrentEventUseCase {
        return GetCurrentEventUseCaseImpl(
            ksatriaMuslimService,
            mediaUrlHelper
        )
    }

    @Provides
    fun getUpcomingEventsUseCase(): GetUpcomingEventsUseCase {
        return InMemoryGetUpcomingEventsUseCase()
    }

    @Provides
    fun getEventDetailUseCase(): GetEventDetailUseCase = InMemoryGetEventDetailUseCase()
}