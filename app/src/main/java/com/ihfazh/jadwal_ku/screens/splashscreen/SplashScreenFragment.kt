package com.ihfazh.jadwal_ku.screens.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.screens.common.fragments.BaseFragment

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment: BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.layout_splash_screen, container, false)
    }
}