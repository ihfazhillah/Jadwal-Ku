package com.ihfazh.jadwal_ku.dependencyinjection.app

import com.ihfazh.jadwal_ku.common.Constants
import com.ihfazh.jadwal_ku.dependencyinjection.MainDispatcher
import com.ihfazh.jadwal_ku.networking.KsatriaMuslimService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Singleton

@Module
class AppModule {
    @MainDispatcher
    @Provides
    fun mainDispatchers(): CoroutineDispatcher = Dispatchers.Main.immediate


    @Singleton
    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Singleton
    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.FULL_DOMAIN)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun ksatriamuslimService(retrofit: Retrofit): KsatriaMuslimService =
        retrofit.create(KsatriaMuslimService::class.java)
}