package com.ihfazh.jadwal_ku.screens.main

import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreenKey
import com.ihfazh.jadwal_ku.screens.common.views.ObservableMvcView

class MainViewMvc (
    layoutInflater: LayoutInflater
): ObservableMvcView<MainViewMvc.Listener>(
    layoutInflater,
    null,
    R.layout.activity_main
) {

    interface Listener {
        fun onBottomMenuChanged(screenKey: ScreenKey)

    }

    val fragmentContent : FrameLayout = findViewById(R.id.frame_content)
    private val bottomNav: BottomNavigationView = findViewById(R.id.bottomNavigation)
    private val layoutParent: LinearLayout = findViewById(R.id.layoutParent)

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

    fun hideBottomNav() {
        animateToggle(layoutParent, bottomNav, false)
    }

    fun showBottomNav() {
        animateToggle(layoutParent, bottomNav, true)
    }

    private fun animateToggle(parent: ViewGroup, target: View, visible: Boolean){
        val transition = Slide(Gravity.BOTTOM)
        transition.duration = 600
        transition.addTarget(target)

        TransitionManager.beginDelayedTransition(parent, transition)
        target.visibility = if (visible) View.VISIBLE else View.GONE
    }
}