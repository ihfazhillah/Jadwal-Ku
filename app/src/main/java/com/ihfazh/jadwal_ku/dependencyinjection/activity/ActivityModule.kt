package com.ihfazh.jadwal_ku.dependencyinjection.activity

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.TransitionInflater
import com.ihfazh.jadwal_ku.screens.common.StringHelper
import com.ihfazh.jadwal_ku.screens.common.ToastHelper
import com.ihfazh.jadwal_ku.screens.common.fragmentframehelper.FragmentFrameWrapper
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

    @Provides
    fun fragmentFrameWrapper(activity: AppCompatActivity) = activity as FragmentFrameWrapper

    @Provides
    fun stringHelper(context: Context) = StringHelper(context)
}