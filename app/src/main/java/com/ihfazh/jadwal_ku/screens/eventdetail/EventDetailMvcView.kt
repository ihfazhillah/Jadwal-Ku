package com.ihfazh.jadwal_ku.screens.eventdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.event.Event
import com.ihfazh.jadwal_ku.event.EventUrlType
import com.ihfazh.jadwal_ku.screens.common.imageloader.ImageLoader
import com.ihfazh.jadwal_ku.screens.common.views.BaseMvcView

class EventDetailMvcView(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    private val imageLoader: ImageLoader
): BaseMvcView<EventDetailMvcView.Listener>(layoutInflater, parent, R.layout.layout_event_detail) {

    interface Listener{
        fun onOpenButtonClick(eventType: EventUrlType)
        fun onRetryButtonClick()
    }

    private val eventDataContainer: ConstraintLayout = findViewById(R.id.eventDataContainer)
    private val errorIndicatorContainer: ConstraintLayout = findViewById(R.id.errorIndicatorContainer)
    private val loadingIndicator: CircularProgressIndicator = findViewById(R.id.eventDetailLoading)

    private val btnOpen: MaterialButton = findViewById(R.id.btnOpen)
    private val imgThumbnail: ImageView = findViewById(R.id.imgThumbnail)
    private val lblOrganizer: TextView = findViewById(R.id.lblOrganizer)
    private val lblTitle: TextView = findViewById(R.id.lblTitle)
    private val lblPresenter: TextView = findViewById(R.id.lblPresenter)
    private val lblDate: TextView = findViewById(R.id.lblDate)
    private val lblTime: TextView = findViewById(R.id.lblTime)


    fun hideEventData() {
        eventDataContainer.visibility = View.GONE
    }

    fun hideErrorIndicator() {
        errorIndicatorContainer.visibility = View.GONE
    }

    fun showLoadingIndicator() {
        loadingIndicator.visibility = View.VISIBLE
    }

    fun bindEvent(event: Event) {
        lblOrganizer.text = event.organizer
        lblTitle.text = event.title
        lblPresenter.text = event.presenter
        lblDate.text = event.date
        lblTime.text = event.time
        imageLoader.loadImage(event.thumbnailUrl, imgThumbnail)
    }

    fun bindOpenButton(eventType: EventUrlType) {
        when(eventType){
            EventUrlType.Youtube -> {
                btnOpen.setIconResource(R.drawable.baseline_smart_display_24)
                btnOpen.visibility = View.VISIBLE
            }
            EventUrlType.Zoom -> {
                btnOpen.setIconResource(R.drawable.baseline_videocam_24)
                btnOpen.visibility = View.VISIBLE
            }
            EventUrlType.Empty -> {
                btnOpen.visibility = View.GONE
            }
        }
    }

    fun showEventData() {
        eventDataContainer.visibility = View.VISIBLE
    }

    fun hideLoadingIndicator() {
        loadingIndicator.visibility = View.GONE
    }

    fun showErrorIndicator() {
        errorIndicatorContainer.visibility = View.VISIBLE
    }


}