package com.ihfazh.jadwal_ku.screens.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.event.Event
import com.ihfazh.jadwal_ku.event.EventListItem
import com.ihfazh.jadwal_ku.screens.common.imageloader.ImageLoader
import com.ihfazh.jadwal_ku.screens.common.views.BaseMvcView
import javax.inject.Inject

class HomeMvcView(
    private val layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    private val imageLoader: ImageLoader
): BaseMvcView<HomeMvcView.Listener>(layoutInflater, parent, R.layout.layout_home) {

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


    private val rvUpcomingEvents: RecyclerView
    private val rvUpcomingEventsAdapter: UpcomingEventsAdapter
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

        rvUpcomingEvents = findViewById(R.id.rvUpcomingEvents)
        rvUpcomingEventsAdapter = UpcomingEventsAdapter()
        rvUpcomingEvents.adapter = rvUpcomingEventsAdapter
        rvUpcomingEvents.layoutManager = LinearLayoutManager(context)

        upcomingLoadingIndicator = findViewById(R.id.upcomingLoading)
        lblUpcomingNoData = findViewById(R.id.lblNoUpcomingEvents)
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
                if (event.youtubeLink !== null){
                    listener.onOpenClick(event.youtubeLink)
                }
                if (event.zoomLink !== null){
                    listener.onOpenClick(event.zoomLink)
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
        rvUpcomingEvents.visibility = View.GONE
    }

    fun showUpcomingEventList() {
        rvUpcomingEvents.visibility = View.VISIBLE
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

    fun bindUpcomingEvents(eventListItem: ArrayList<EventListItem>) {
        rvUpcomingEventsAdapter.bindItems(eventListItem)
    }


    inner class UpcomingEventsAdapter: RecyclerView.Adapter<UpcomingEventsAdapter.ViewHolder>(){
        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val imgThumbnail: ImageView = view.findViewById(R.id.imgThumbnail)
            val lblTitle: TextView = view.findViewById(R.id.lblTitle)
            val lblDateTime: TextView = view.findViewById(R.id.lblDateTime)
        }

        private val items: MutableList<EventListItem> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.list_item_event, parent, false)
            return ViewHolder(view)
        }

        fun bindItems(listItems: List<EventListItem>){
            items.clear()
            items.addAll(listItems)
            notifyDataSetChanged()
        }


        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val eventItem = items[position]
            imageLoader.loadImage(eventItem.thumbnailUrl, holder.imgThumbnail)
            holder.lblTitle.text = eventItem.title
            holder.lblDateTime.text = "${eventItem.date} ${eventItem.time}"
            holder.itemView.setOnClickListener{
                listeners.forEach { listener ->
                    listener.onUpcomingClick(eventItem.id)
                }
            }
        }

    }

}