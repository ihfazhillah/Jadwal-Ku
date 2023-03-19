package com.ihfazh.jadwal_ku.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.screens.common.activities.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject lateinit var viewMvc: MainViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(viewMvc.rootView)
    }
}