package com.ihfazh.jadwal_ku.screens.eventlist

import javax.inject.Inject

class EventListStateManager @Inject constructor(){
    var page: Int = 1
    var loading: Boolean = false
    var hasNext: Boolean = true

    fun reset(){
        page = 1
    }

    fun nextPage(){
        page += 1
    }

    fun canGoNext() = !loading && hasNext
}