package com.ihfazh.jadwal_ku.dependencyinjection.activity

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.ihfazh.jadwal_ku.screens.common.ToastHelper
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {

    @Provides
    fun layoutInflater(activity: AppCompatActivity) = LayoutInflater.from(activity)

    @Provides
    fun fragmentManager(activity: AppCompatActivity) = activity.supportFragmentManager

    @Provides
    fun toastHelper(activity: AppCompatActivity) = ToastHelper(activity)
}