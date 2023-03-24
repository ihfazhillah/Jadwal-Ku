package com.ihfazh.jadwal_ku

import android.app.Application
import com.ihfazh.jadwal_ku.dependencyinjection.app.AppComponent
import com.ihfazh.jadwal_ku.dependencyinjection.app.DaggerAppComponent
import dagger.internal.DaggerCollections

class MyApplication: Application() {
    val appComponent : AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }
    override fun onCreate() {
        appComponent.inject(this)
        super.onCreate()
    }
}