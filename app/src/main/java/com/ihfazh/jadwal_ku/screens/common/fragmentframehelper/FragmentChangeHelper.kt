package com.ihfazh.jadwal_ku.screens.common.fragmentframehelper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.ihfazh.jadwal_ku.common.BaseObservable
import com.ihfazh.jadwal_ku.dependencyinjection.activity.ActivityScope
import javax.inject.Inject

@ActivityScope
class FragmentChangeHelper @Inject constructor(
    fragmentManager: FragmentManager,
): BaseObservable<FragmentChangeHelper.Listener>() {
    interface Listener {
       fun onFragmentChanged(fragment: Fragment)
    }

    init {

        fragmentManager.registerFragmentLifecycleCallbacks(object : FragmentLifecycleCallbacks() {
            override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
                listeners.forEach {  listener -> listener.onFragmentChanged(f) }
            }

        }, false)

    }

}