package com.ihfazh.jadwal_ku.screens.common.toolbar

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.appbar.MaterialToolbar
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.screens.common.views.BaseMvcView

class ToolbarMvcView(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
): BaseMvcView(layoutInflater, parent, R.layout.layout_toolbar) {

    interface OnBackPressedListener {
        fun onBackPressed()
    }

    lateinit var onBackPressedListener: OnBackPressedListener

    private val toolbar: MaterialToolbar = findViewById(R.id.layout_toolbar)

    fun setTitle(title: String){
        toolbar.title = title
    }

    fun enableUpButtonAndListen(listener: OnBackPressedListener){
        onBackPressedListener = listener
        toolbar.setNavigationOnClickListener {
            onBackPressedListener.onBackPressed()
        }
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
    }

}