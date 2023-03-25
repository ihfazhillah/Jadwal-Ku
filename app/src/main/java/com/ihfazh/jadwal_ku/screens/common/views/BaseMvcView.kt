package com.ihfazh.jadwal_ku.screens.common.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

open class BaseMvcView<LISTENER_TYPE>(
    private val layoutInflater: LayoutInflater,
    private val parent: ViewGroup?,
    @LayoutRes private val layoutId: Int
) {
    val rootView: View = layoutInflater.inflate(layoutId, parent, false)

    protected fun <T : View> findViewById(id: Int) : T{
        return rootView.findViewById(id)
    }

    protected val listeners: HashSet<LISTENER_TYPE> = HashSet()

    fun registerListener(listener: LISTENER_TYPE){
        listeners.add(listener)
    }

    fun unregisterListener(listener: LISTENER_TYPE){
        listeners.remove(listener)
    }
}