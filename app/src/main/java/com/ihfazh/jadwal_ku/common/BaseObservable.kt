package com.ihfazh.jadwal_ku.common

open class BaseObservable<LISTENER_TYPE> {
    protected val listeners = HashSet<LISTENER_TYPE>()

    fun registerListener(listener: LISTENER_TYPE){
        listeners.add(listener)
    }

    fun unregisterListener(listener: LISTENER_TYPE){
        listeners.remove(listener)
    }

}