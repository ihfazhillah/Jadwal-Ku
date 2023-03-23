package com.ihfazh.jadwal_ku.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ihfazh.jadwal_ku.screens.common.fragments.BaseFragment
import com.ihfazh.jadwal_ku.screens.common.views.ViewMvcFactory
import javax.inject.Inject

class HomeFragment: BaseFragment() {
    lateinit var mvcView: HomeMvcView
    @Inject lateinit var mvcFactory: ViewMvcFactory
    @Inject lateinit var homeController: HomeController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mvcView = mvcFactory.newHomeMvc(container)
        homeController.bindView(mvcView)

        return mvcView.rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        homeController.onStart()
    }

    override fun onStop() {
        super.onStop()
        homeController.onStop()
    }
}