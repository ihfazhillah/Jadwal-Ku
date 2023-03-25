package com.ihfazh.jadwal_ku.screens.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.event.Event
import com.ihfazh.jadwal_ku.screens.common.imageloader.ImageLoader
import com.ihfazh.jadwal_ku.screens.common.views.BaseMvcView
import javax.inject.Inject

class HomeMvcView(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    private val imageLoader: ImageLoader
): BaseMvcView<HomeMvcView.Listener>(layoutInflater, parent, R.layout.layout_home) {

    private val eventLoadingContainer : ConstraintLayout
    private val eventNotFoundContainer: ConstraintLayout
    private val eventContainer: ConstraintLayout

    private val eventThumbnail: ImageView
    private val lblOrganizer: TextView
    private val lblTitle: TextView
    private val lblPresenter: TextView
    private val lblDate: TextView
    private val lblTime: TextView
    private val btnOpen: MaterialButton
    private val btnReload: MaterialButton

    init {
        eventNotFoundContainer = findViewById(R.id.eventNotFoundContainer)
        eventLoadingContainer = findViewById(R.id.eventLoadingContainer)
        eventContainer = findViewById(R.id.eventFoundContainer)

        eventThumbnail = findViewById(R.id.imgThumbnail)
        lblOrganizer = findViewById(R.id.lblOrganizer)
        lblTitle = findViewById(R.id.lblTitle)
        lblPresenter = findViewById(R.id.lblPresenter)
        lblDate = findViewById(R.id.lblDate)
        lblTime = findViewById(R.id.lblTime)
        btnOpen = findViewById(R.id.btnOpen)


        btnReload = findViewById(R.id.btnReload)
        btnReload.setOnClickListener {
            listeners.forEach { listener ->
                listener.onReloadClick()
            }
        }

    }


    fun bindCurrentEvent(event: Event) {
        lblOrganizer.text = event.organizer
        lblTitle.text = event.title
        lblPresenter.text = event.presenter
        lblDate.text = event.date
        lblTime.text = event.time
        imageLoader.loadImage(event.thumbnailUrl, eventThumbnail)
    }

    fun showCurrentEventIndicator() {
        eventLoadingContainer.visibility = View.VISIBLE
    }

    fun hideCurrentEventIndicator() {
        eventLoadingContainer.visibility = View.GONE
    }

    fun showCurrentEventEmpty() {
        eventNotFoundContainer.visibility = View.VISIBLE
    }

    fun showCurrentEvent() {
        eventContainer.visibility = View.VISIBLE
    }

    fun hideCurrentEventEmpty() {
        eventNotFoundContainer.visibility = View.GONE
    }

    interface Listener{
        fun onReloadClick()

    }

}