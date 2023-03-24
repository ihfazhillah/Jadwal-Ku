package com.ihfazh.jadwal_ku.dependencyinjection.presentation

import com.ihfazh.jadwal_ku.event.GetCurrentEventUseCase
import com.ihfazh.jadwal_ku.event.InMemoryGetCurrentEventUseCase
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {
    @Provides
    fun getCurrentEventUseCase(): GetCurrentEventUseCase {
        return InMemoryGetCurrentEventUseCase()
    }
}