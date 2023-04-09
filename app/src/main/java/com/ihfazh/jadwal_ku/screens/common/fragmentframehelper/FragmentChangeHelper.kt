package com.ihfazh.jadwal_ku.screens.common.fragmentframehelper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ihfazh.jadwal_ku.common.BaseObservable
import com.ihfazh.jadwal_ku.dependencyinjection.activity.ActivityScope
import javax.inject.Inject

@ActivityScope
class FragmentChangeHelper @Inject constructor(
    private val fragmentManager: FragmentManager,
    private val fragmentFrameWrapper: FragmentFrameWrapper
): BaseObservable<FragmentChangeHelper.Listener>() {
    interface Listener {
       fun onFragmentChanged(fragment: Fragment)
    }

    init {
        fragmentManager.addOnBackStackChangedListener {
            val currentFragment = getCurrentFragment()
            if (currentFragment != null){
                listeners.forEach {  listener -> listener.onFragmentChanged(currentFragment) }
            }

        }
    }

    private fun getCurrentFragment() : Fragment? = fragmentManager.findFragmentById(fragmentFrameId)

    private val fragmentFrameId get() = fragmentFrameWrapper.fragmentFrame.id
}