package com.ihfazh.jadwal_ku.screens.main

import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.screens.common.views.BaseMvcView
import javax.inject.Inject

class MainViewMvc (
    layoutInflater: LayoutInflater
): BaseMvcView<MainViewMvc.Listener>(
    layoutInflater,
    null,
    R.layout.activity_main
) {
    interface Listener {

    }

    val fragmentContent : FrameLayout = findViewById(R.id.frame_content)
}