package com.ihfazh.jadwal_ku.screens.eventdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.event.Event
import com.ihfazh.jadwal_ku.event.EventUrlType
import com.ihfazh.jadwal_ku.screens.common.imageloader.ImageLoader
import com.ihfazh.jadwal_ku.screens.common.toolbar.ToolbarMvcView
import com.ihfazh.jadwal_ku.screens.common.views.ObservableMvcView

class EventDetailMvcView(
    private val layoutInflater: LayoutInflater,
    private val parent: ViewGroup?,
    private val imageLoader: ImageLoader
): ObservableMvcView<EventDetailMvcView.Listener>(layoutInflater, parent, R.layout.layout_event_detail),
    ToolbarMvcView.OnBackPressedListener {

    interface Listener{
        fun onOpenButtonClick()
        fun onRetryButtonClick()
        fun onBackClick()

        fun onImageClick(imageUrl: String, view: View)
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


    init {

        setupToolbar()
    }

    private fun setupToolbar() {
        val toolbarView = ToolbarMvcView(layoutInflater, parent)
        toolbarView.setTitle("Event Detil")
        toolbarView.enableUpButtonAndListen(this)

        val toolbar: FrameLayout = findViewById(R.id.toolbar)
        toolbar.addView(toolbarView.rootView)
    }


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
        imgThumbnail.setOnClickListener { view ->
            listeners.forEach {  listener -> listener.onImageClick(event.thumbnailUrl, view) }
        }
    }

    fun bindOpenButton(eventType: EventUrlType) {
        when(eventType){
            EventUrlType.Youtube -> {
                btnOpen.setIconResource(R.drawable.baseline_smart_display_24)
                btnOpen.text = context.getText(R.string.open_youtube)
                btnOpen.visibility = View.VISIBLE
            }
            EventUrlType.Zoom -> {
                btnOpen.setIconResource(R.drawable.baseline_videocam_24)
                btnOpen.text = context.getText(R.string.open_zoom)
                btnOpen.visibility = View.VISIBLE
            }
            EventUrlType.Empty -> {
                btnOpen.visibility = View.GONE
            }
        }
        btnOpen.setOnClickListener {
            listeners.forEach { listener ->
                listener.onOpenButtonClick()
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

    override fun onBackPressed() {
        listeners.forEach { listener -> listener.onBackClick() }
    }


}