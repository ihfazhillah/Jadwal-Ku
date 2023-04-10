package com.ihfazh.jadwal_ku.screens.common.screensnavigator

import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.dependencyinjection.activity.ActivityScope
import com.ihfazh.jadwal_ku.screens.common.fragmentframehelper.FragmentFrameHelper
import com.ihfazh.jadwal_ku.screens.common.fragmentframehelper.FragmentTransactionHelper
import com.ihfazh.jadwal_ku.screens.eventdetail.EventDetailFragment
import com.ihfazh.jadwal_ku.screens.home.HomeFragment
import com.ihfazh.jadwal_ku.screens.login.LoginFragment
import com.ihfazh.jadwal_ku.screens.settings.SettingsFragment
import com.ihfazh.jadwal_ku.screens.splashscreen.SplashScreenFragment
import com.ihfazh.jadwal_ku.screens.thumbnailview.ThumbnailViewFragment
import javax.inject.Inject

class ScreensNavigator @Inject constructor(
    private val fragmentFrameHelper: FragmentFrameHelper,
) {
    fun goToHome(){
        fragmentFrameHelper.replaceFragmentDontAddToBackstack(HomeFragment())
    }

    fun goToHomeAndAddToBackstacks() {
        fragmentFrameHelper.replaceFragment(HomeFragment())
    }

    fun goToLogin() {
        fragmentFrameHelper.replaceFragmentAndClearBackstack(LoginFragment())
    }

    fun goToSplashScreen() {
        fragmentFrameHelper.replaceFragmentDontAddToBackstack(SplashScreenFragment())
    }

    fun goToEventDetail(eventId: String) {
        val eventDetailFragment = EventDetailFragment.newInstance(eventId)
        fragmentFrameHelper.replaceFragment(eventDetailFragment)
    }

    fun goToEventDetail(eventId: String, ftCombiner: (FragmentTransaction) -> FragmentTransaction){
        val eventDetailFragment = EventDetailFragment.newInstance(eventId)
        fragmentFrameHelper.replaceFragment(eventDetailFragment, ftCombiner)
    }

    fun goToEvents() {
        /* noop for now */
    }

    fun goToSettings() {
        val settingsFragment = SettingsFragment()
        fragmentFrameHelper.replaceFragmentAndClearBackstack(settingsFragment)
    }


    fun navigateUp() {
        fragmentFrameHelper.navigateUp()
    }

    fun goToImageThumbnailView(url: String) {
        val fragment = ThumbnailViewFragment.newInstance(url)
        fragmentFrameHelper.replaceFragment(fragment)
    }
    fun goToImageThumbnailView(url: String, ftCombiner: (FragmentTransaction) -> FragmentTransaction){
        val eventDetailFragment = ThumbnailViewFragment.newInstance(url)
        fragmentFrameHelper.replaceFragment(eventDetailFragment, ftCombiner)
    }

}