package com.ihfazh.jadwal_ku.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ihfazh.jadwal_ku.screens.common.fragments.BaseFragment
import com.ihfazh.jadwal_ku.screens.common.views.ViewMvcFactory
import javax.inject.Inject

class LoginFragment: BaseFragment() {
    lateinit var mvcView: LoginViewMvc
    @Inject lateinit var mvcFactory: ViewMvcFactory
    @Inject lateinit var loginController: LoginViewController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mvcView = mvcFactory.newLoginMvcView(container)
        loginController.bindView(mvcView)

        return mvcView.rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        loginController.onStart()
    }

    override fun onStop() {
        super.onStop()
        loginController.onStop()
    }
}