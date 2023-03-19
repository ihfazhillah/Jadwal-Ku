package com.ihfazh.jadwal_ku.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.screens.common.activities.BaseActivity
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import com.ihfazh.jadwal_ku.screens.common.views.ViewMvcFactory
import com.ihfazh.jadwal_ku.screens.home.HomeFragment
import javax.inject.Inject

class MainActivity : BaseActivity() {
    lateinit var viewMvc: MainViewMvc
    @Inject lateinit var mvcFactory: ViewMvcFactory
    @Inject lateinit var screensNavigator: ScreensNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)

        viewMvc = mvcFactory.newMainActivityMvc()

        super.onCreate(savedInstanceState)

        setContentView(viewMvc.rootView)

        if (savedInstanceState == null){
            screensNavigator.goToHome()
        }
    }
}