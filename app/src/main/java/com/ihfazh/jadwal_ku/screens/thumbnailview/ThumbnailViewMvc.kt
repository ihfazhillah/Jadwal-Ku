package com.ihfazh.jadwal_ku.screens.thumbnailview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.screens.common.imageloader.ImageLoader
import com.ihfazh.jadwal_ku.screens.common.toolbar.ToolbarMvcView
import com.ihfazh.jadwal_ku.screens.common.views.ObservableMvcView

class ThumbnailViewMvc(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    private val imageLoader: ImageLoader
): ObservableMvcView<ThumbnailViewMvc.Listener>(layoutInflater, parent, R.layout.layout_thumbnail_view),
    ToolbarMvcView.OnBackPressedListener {

    interface Listener {
        fun onBackPressed()
    }

    private val imageView : ImageView = findViewById(R.id.imgView)

    init {
        val toolbar = findViewById<FrameLayout>(R.id.toolbar)
        val toolbarViewMvc = ToolbarMvcView(layoutInflater, parent)
        toolbarViewMvc.enableUpButtonAndListen(this)
        toolbar.addView(toolbarViewMvc.rootView)
    }

    fun bindImage(url: String){
        imageLoader.loadImageCenterInside(url, imageView)
    }

    override fun onBackPressed() {
        listeners.forEach { listener -> listener.onBackPressed() }
    }
}