package com.ihfazh.jadwal_ku.dependencyinjection.app

import com.ihfazh.jadwal_ku.dependencyinjection.MainDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class AppModule {
    @MainDispatcher
    @Provides
    fun mainDispatchers(): CoroutineDispatcher = Dispatchers.Main.immediate
}