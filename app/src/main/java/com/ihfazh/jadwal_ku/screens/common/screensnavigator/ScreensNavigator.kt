package com.ihfazh.jadwal_ku.screens.common.screensnavigator

import androidx.fragment.app.FragmentManager
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.screens.common.fragmentframehelper.FragmentFrameHelper
import com.ihfazh.jadwal_ku.screens.eventdetail.EventDetailFragment
import com.ihfazh.jadwal_ku.screens.home.HomeFragment
import javax.inject.Inject

class ScreensNavigator @Inject constructor(
    private val fragmentFrameHelper: FragmentFrameHelper
) {
    fun goToHome(){
        fragmentFrameHelper.replaceFragmentDontAddToBackstack(HomeFragment())
    }

    fun goToEventDetail(eventId: String) {
        val eventDetailFragment = EventDetailFragment.newInstance(eventId)
        fragmentFrameHelper.replaceFragment(eventDetailFragment)
    }

    fun navigateUp() {
        fragmentFrameHelper.navigateUp()
    }

}