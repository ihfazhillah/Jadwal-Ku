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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mvcView = mvcFactory.newHomeMvc(container)

        return mvcView.rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
    }
}