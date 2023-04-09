package com.ihfazh.jadwal_ku.screens.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.event.Event
import com.ihfazh.jadwal_ku.event.EventLink
import com.ihfazh.jadwal_ku.event.EventListItem
import com.ihfazh.jadwal_ku.screens.common.imageloader.ImageLoader
import com.ihfazh.jadwal_ku.screens.common.listviewhelper.AdjustHeightHelper
import com.ihfazh.jadwal_ku.screens.common.toolbar.ToolbarMvcView
import com.ihfazh.jadwal_ku.screens.common.views.ObservableMvcView

class HomeMvcView(
    private val layoutInflater: LayoutInflater,
    private val parent: ViewGroup?,
    private val imageLoader: ImageLoader,
    private val adjustHeightHelper: AdjustHeightHelper
): ObservableMvcView<HomeMvcView.Listener>(layoutInflater, parent, R.layout.layout_home) {

    interface Listener{
        fun onReloadClick()
        fun onOpenClick(url: String)
        fun onUpcomingClick(id: String)
    }

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


    private val upcomingEvents: ListView
    private val upcomingLoadingIndicator: CircularProgressIndicator
    private val lblUpcomingNoData: TextView

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

        upcomingEvents = findViewById(R.id.upcomingEvents)

        upcomingLoadingIndicator = findViewById(R.id.upcomingLoading)
        lblUpcomingNoData = findViewById(R.id.lblNoUpcomingEvents)

        setupToolbar()
    }


    fun bindCurrentEvent(event: Event) {
        lblOrganizer.text = event.organizer
        lblTitle.text = event.title
        lblPresenter.text = event.presenter
        lblDate.text = event.date
        lblTime.text = event.time
        imageLoader.loadImage(event.thumbnailUrl, eventThumbnail)

        btnOpen.setOnClickListener {
            listeners.forEach { listener ->
                when (val link = event.link){
                    EventLink.EmptyLink -> {
                        // noop
                    }
                    is EventLink.YoutubeLink -> listener.onOpenClick(link.link)
                    is EventLink.ZoomLink -> listener.onOpenClick(link.link)
                }
            }
        }

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

    fun hideCurrentEvent() {
        eventContainer.visibility = View.GONE
    }

    fun hideUpcomingEventList() {
        upcomingEvents.visibility = View.GONE
    }

    fun showUpcomingEventList() {
        upcomingEvents.visibility = View.VISIBLE
    }

    fun showUpcomingEventLoadingIndicator() {
        upcomingLoadingIndicator.visibility = View.VISIBLE
    }

    fun hideUpcomingEventLoadingIndicator() {
        upcomingLoadingIndicator.visibility = View.GONE
    }

    fun hideUpcomingNoData() {
        lblUpcomingNoData.visibility = View.GONE
    }

    fun showUpcomingEventNoData() {
        lblUpcomingNoData.visibility = View.VISIBLE
    }

    fun bindUpcomingEvents(eventListItem: List<EventListItem>) {
        val adapter = UpcomingEventsListViewAdapter(context, eventListItem)
        upcomingEvents.adapter = adapter
        adjustHeightHelper.adjustListViewHeight(eventListItem, upcomingEvents)
    }


    inner class UpcomingEventsListViewAdapter(
        context: Context,
        eventListItem: List<EventListItem>
    ): ArrayAdapter<EventListItem>(context, 0, eventListItem){

        inner class ViewHolder(view: View){
            val imgThumbnail: ImageView = view.findViewById(R.id.imgThumbnail)
            val lblTitle: TextView = view.findViewById(R.id.lblTitle)
            val lblDateTime: TextView = view.findViewById(R.id.lblDateTime)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val view = convertView ?: layoutInflater.inflate(R.layout.list_item_event, parent, false)

            val holder: ViewHolder = if (convertView != null){
                view.tag as ViewHolder
            } else {
                ViewHolder(view).also {
                    view.tag = it
                }
            }

            val item: EventListItem? = getItem(position)

            if (item != null){
                imageLoader.loadImage(item.thumbnailUrl, holder.imgThumbnail)
                holder.lblTitle.text = item.title
                holder.lblDateTime.text = "${item.date} ${item.time}"
                view.setOnClickListener{
                    listeners.forEach { listener ->
                        listener.onUpcomingClick(item.id)
                    }
                }
            }

            return view
        }
    }

    private fun setupToolbar() {
        val toolbarView = ToolbarMvcView(layoutInflater, parent)
        toolbarView.setTitle("Jadwal-Ku")

        val toolbar: FrameLayout = findViewById(R.id.toolbar)
        toolbar.addView(toolbarView.rootView)
    }
}