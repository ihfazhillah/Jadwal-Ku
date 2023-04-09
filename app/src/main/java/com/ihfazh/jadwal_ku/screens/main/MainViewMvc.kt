package com.ihfazh.jadwal_ku.screens.main

import android.bluetooth.le.ScanSettings
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreenKey
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
        fun onBottomMenuChanged(screenKey: ScreenKey)

    }

    val fragmentContent : FrameLayout = findViewById(R.id.frame_content)
    val bottomNav: BottomNavigationView = findViewById(R.id.bottomNavigation)

    init {
        bottomNav.setOnItemSelectedListener { selectedItem ->
            val screenKey = when(selectedItem.itemId){
                R.id.menuHome -> ScreenKey.HOME
                R.id.menuList -> ScreenKey.EVENTS
                R.id.menuSettings -> ScreenKey.SETTINGS
                else -> throw RuntimeException("Menu not registered")
            }
            listeners.forEach { listener -> listener.onBottomMenuChanged(screenKey) }
            true
        }

    }
}