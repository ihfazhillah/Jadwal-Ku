package com.ihfazh.jadwal_ku.screens.common.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

open class ObservableMvcView<LISTENER_TYPE>(
    private val layoutInflater: LayoutInflater,
    private val parent: ViewGroup?,
    @LayoutRes private val layoutId: Int
) : BaseMvcView(layoutInflater, parent, layoutId) {

    protected val listeners: HashSet<LISTENER_TYPE> = HashSet()

    fun registerListener(listener: LISTENER_TYPE){
        listeners.add(listener)
    }

    fun unregisterListener(listener: LISTENER_TYPE){
        listeners.remove(listener)
    }
}