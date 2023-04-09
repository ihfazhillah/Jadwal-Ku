package com.ihfazh.jadwal_ku.screens.thumbnailview

import com.ihfazh.jadwal_ku.screens.common.screensnavigator.ScreensNavigator
import javax.inject.Inject

class ThumbnailViewController @Inject constructor(
    private val navigator: ScreensNavigator
) : ThumbnailViewMvc.Listener {
    lateinit var viewMvc: ThumbnailViewMvc

    fun bindViewMvc(viewMvc: ThumbnailViewMvc){
        this.viewMvc = viewMvc
    }

    fun bindImage(url: String) {
        viewMvc.bindImage(url)
    }

    fun onStart(){
        viewMvc.registerListener(this)

    }

    fun onStop(){
        viewMvc.unregisterListener(this)
    }

    override fun onBackPressed() {
        navigator.navigateUp()
    }
}