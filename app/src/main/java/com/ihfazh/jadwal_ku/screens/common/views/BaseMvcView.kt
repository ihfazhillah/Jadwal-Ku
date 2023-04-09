package com.ihfazh.jadwal_ku.screens.common.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

open class BaseMvcView(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    @LayoutRes private val layoutId: Int
) {
    val rootView: View = layoutInflater.inflate(layoutId, parent, false)
    protected val context: Context get() = rootView.context
    protected fun <T : View> findViewById(id: Int): T {
        return rootView.findViewById(id)
    }
}