package com.ihfazh.jadwal_ku.screens.eventlist

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.event.EventListItem
import com.ihfazh.jadwal_ku.screens.common.imageloader.ImageLoader
import com.ihfazh.jadwal_ku.screens.common.toolbar.ToolbarMvcView
import com.ihfazh.jadwal_ku.screens.common.views.ObservableMvcView

class EventListViewMvc(
    private val layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    private val imageLoader: ImageLoader
): ObservableMvcView<EventListViewMvc.Listener>(layoutInflater, parent, R.layout.layout_events) {

    interface Listener {
        fun onEventClick(eventId: String)
    }

    val toolbar: FrameLayout = findViewById(R.id.toolbar)
    private val toolbarViewMvc = ToolbarMvcView(layoutInflater, parent)

    private val rvUpcoming: RecyclerView = findViewById(R.id.rvUpcoming)
    private val adapter = UpcomingEventsRecyclerViewAdapter()

    init {
        setupToolbar()
        rvUpcoming.adapter = adapter
    }

    fun bindEvents(events: List<EventListItem>){
        adapter.bindEvents(events)
    }

    private fun setupToolbar() {
        toolbarViewMvc.setTitle("Akan Datang")
        toolbar.addView(toolbarViewMvc.rootView)
    }


    inner class UpcomingEventsRecyclerViewAdapter: RecyclerView.Adapter<UpcomingEventsRecyclerViewAdapter.ViewHolder>(){
        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val imgThumbnail: ImageView = view.findViewById(R.id.imgThumbnail)
            val lblTitle: TextView = view.findViewById(R.id.lblTitle)
            val lblDateTime: TextView = view.findViewById(R.id.lblDateTime)
        }

        private val events: ArrayList<EventListItem> = arrayListOf()

        fun bindEvents(events: List<EventListItem>){
            this.events.clear()
            this.events.addAll(events)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.list_item_event, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return events.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val event = events[position]
            holder.lblTitle.text = event.title
            holder.lblDateTime.text = "${event.date} ${event.time}"
            imageLoader.loadImage(event.thumbnailUrl, holder.imgThumbnail)
            holder.itemView.setOnClickListener {
                listeners.forEach { listener -> listener.onEventClick(event.id) }
            }
        }
    }



}