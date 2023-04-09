package com.ihfazh.jadwal_ku.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ihfazh.jadwal_ku.screens.common.fragments.BaseFragment
import com.ihfazh.jadwal_ku.screens.common.views.ViewMvcFactory
import javax.inject.Inject

class SettingsFragment: BaseFragment() {
    lateinit var viewMvc: SettingsViewMvc
    @Inject lateinit var viewMvcFactory: ViewMvcFactory
    @Inject lateinit var controller: SettingsController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewMvc = viewMvcFactory.newSettingsView(container)
        controller.bindView(viewMvc)
        return viewMvc.rootView
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        controller.onStart()
    }

    override fun onStop() {
        super.onStop()
        controller.onStop()
    }
}