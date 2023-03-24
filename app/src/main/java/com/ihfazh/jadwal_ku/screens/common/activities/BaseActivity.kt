package com.ihfazh.jadwal_ku.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.ihfazh.jadwal_ku.MyApplication
import com.ihfazh.jadwal_ku.dependencyinjection.activity.ActivityComponent
import com.ihfazh.jadwal_ku.dependencyinjection.activity.ActivityModule

open class BaseActivity: AppCompatActivity() {

    private val appComponent by lazy {
        (application as MyApplication).appComponent
    }

    val activityComponent: ActivityComponent by lazy {
        appComponent.newActivityComponent().activity(this).build()
    }

}