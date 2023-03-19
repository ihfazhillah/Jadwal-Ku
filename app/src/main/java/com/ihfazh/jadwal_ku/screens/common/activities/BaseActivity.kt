package com.ihfazh.jadwal_ku.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.ihfazh.jadwal_ku.dependencyinjection.activity.DaggerActivityComponent

open class BaseActivity: AppCompatActivity() {

    val injector by lazy {
        DaggerActivityComponent.builder()
            .activity(this)
            .build()
    }


}