package com.ihfazh.jadwal_ku.screens.common.screensnavigator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.screens.home.HomeFragment
import javax.inject.Inject

class ScreensNavigator @Inject constructor(
    private val fragmentManager: FragmentManager
) {
    fun goToHome(){
        fragmentManager.beginTransaction()
        .add(R.id.frame_content, HomeFragment())
        .commit()
    }

    fun goToEventDetail(eventId: String) {
        // todo
    }

}