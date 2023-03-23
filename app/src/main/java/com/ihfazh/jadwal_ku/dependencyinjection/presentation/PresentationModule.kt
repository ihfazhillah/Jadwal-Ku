package com.ihfazh.jadwal_ku.dependencyinjection.presentation

import com.ihfazh.jadwal_ku.dependencyinjection.MainDispatcher
import com.ihfazh.jadwal_ku.event.GetCurrentEventUseCase
import com.ihfazh.jadwal_ku.event.InMemoryGetCurrentEventUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class PresentationModule {
    @Provides
    fun getCurrentEventUseCase(): GetCurrentEventUseCase {
        return InMemoryGetCurrentEventUseCase()
    }

    @MainDispatcher
    @Provides
    fun mainDispatchers(): CoroutineDispatcher = Dispatchers.Main.immediate
}