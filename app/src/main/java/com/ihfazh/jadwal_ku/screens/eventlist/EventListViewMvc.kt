package com.ihfazh.jadwal_ku.screens.eventlist

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
        fun onLastEventItemReached()
    }

    val toolbar: FrameLayout = findViewById(R.id.toolbar)
    private val toolbarViewMvc = ToolbarMvcView(layoutInflater, parent)

    private val rvUpcoming: RecyclerView = findViewById(R.id.rvUpcoming)
    private val adapter = UpcomingEventsRecyclerViewAdapter()

    init {
        setupToolbar()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rvUpcoming.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvUpcoming.layoutManager = layoutManager

        rvUpcoming.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1){
                    rvUpcoming.post{
                        listeners.forEach { listener -> listener.onLastEventItemReached() }
                    }
                }

            }
        })
    }

    fun bindEvents(events: List<EventListItem>){
        rvUpcoming.post{
            adapter.bindEvents(events)
        }
    }

    fun showLoadingIndicator(){
        rvUpcoming.post {
            adapter.showLoadingIndicator()
        }
    }

    fun hideLoadingIndicator(){
        rvUpcoming.post {
            adapter.hideLoadingIndicator()
        }
    }

    private fun setupToolbar() {
        toolbarViewMvc.setTitle("Akan Datang")
        toolbar.addView(toolbarViewMvc.rootView)
    }


    inner class UpcomingEventsRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {


        inner class EventItemViewHolder(view: View): RecyclerView.ViewHolder(view){
            val imgThumbnail: ImageView = view.findViewById(R.id.imgThumbnail)
            val lblTitle: TextView = view.findViewById(R.id.lblTitle)
            val lblDateTime: TextView = view.findViewById(R.id.lblDateTime)
        }

        inner class EventLoadingViewHolder(view: View): RecyclerView.ViewHolder(view){
            /* no op */
        }

        private val events: ArrayList<EventListItem?> = arrayListOf()

        fun bindEvents(events: List<EventListItem>){
            // Fixme: how to handle notify only added
            // fixme: how to preserve the list when action back
            val currentIndex = events.size
            this.events.addAll(events)
//            no(currentIndex, events.size)
            notifyDataSetChanged()
        }

        fun showLoadingIndicator(){
            this.events.add(null)
            notifyItemInserted(this.events.size)
        }

        fun hideLoadingIndicator(){
            val lastIndex = this.events.size
            this.events.remove(null)
            notifyItemRemoved(lastIndex)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (viewType == EVENT_ITEM_TYPE){
                val view = layoutInflater.inflate(R.layout.list_item_event, parent, false)
                EventItemViewHolder(view)
            } else {
                val view = layoutInflater.inflate(R.layout.layout_item_loading, parent, false)
                EventLoadingViewHolder(view)
            }
        }

        override fun getItemCount(): Int {
            return events.size
        }

        override fun getItemViewType(position: Int): Int {
            val event = events[position]
            return if (event === null){
                EVENT_LOADING_TYPE
            } else {
                EVENT_ITEM_TYPE
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is EventItemViewHolder){
            val event = events[position] ?: return
                holder.lblTitle.text = event.title
                holder.lblDateTime.text = "${event.date} ${event.time}"
                imageLoader.loadImage(event.thumbnailUrl, holder.imgThumbnail)
                holder.itemView.setOnClickListener {
                    listeners.forEach { listener -> listener.onEventClick(event.id) }
                }

            }
        }

    }

    companion object {
        private const val  EVENT_ITEM_TYPE = 1
        private const val EVENT_LOADING_TYPE = 2
    }



}