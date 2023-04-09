package com.ihfazh.jadwal_ku.dependencyinjection.app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.TransitionInflater
import com.ihfazh.jadwal_ku.MyApplication
import com.ihfazh.jadwal_ku.common.Constants
import com.ihfazh.jadwal_ku.dependencyinjection.MainDispatcher
import com.ihfazh.jadwal_ku.networking.AuthInterceptor
import com.ihfazh.jadwal_ku.networking.KsatriaMuslimService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    fun transitionInflater(application: Application) : TransitionInflater {
        return TransitionInflater.from(application)
    }
    @MainDispatcher
    @Provides
    fun mainDispatchers(): CoroutineDispatcher = Dispatchers.Main.immediate

    @Provides
    fun provideContext(application: Application): Context = application


    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor) = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(authInterceptor)
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