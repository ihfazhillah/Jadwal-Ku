package com.ihfazh.jadwal_ku.screens.main

import android.view.LayoutInflater
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.screens.common.views.BaseMvcView
import javax.inject.Inject

class MainViewMvc @Inject constructor(
    layoutInflater: LayoutInflater
): BaseMvcView<MainViewMvc.Listener>(
    layoutInflater,
    null,
    R.layout.activity_main
) {
    interface Listener {

    }
}