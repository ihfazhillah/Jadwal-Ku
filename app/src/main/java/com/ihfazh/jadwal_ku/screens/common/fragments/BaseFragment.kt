package com.ihfazh.jadwal_ku.screens.common.fragments

import androidx.fragment.app.Fragment
import com.ihfazh.jadwal_ku.dependencyinjection.activity.ActivityComponent
import com.ihfazh.jadwal_ku.dependencyinjection.presentation.PresentationComponent
import com.ihfazh.jadwal_ku.dependencyinjection.presentation.PresentationModule
import com.ihfazh.jadwal_ku.screens.common.activities.BaseActivity
import com.ihfazh.jadwal_ku.screens.main.MainActivity

open class BaseFragment: Fragment() {
    private val activityComponent: ActivityComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent
    }
    val injector: PresentationComponent by lazy {
        activityComponent.newPresentationComponent(PresentationModule())
    }
}