package com.ihfazh.jadwal_ku.event

data class Event(
    val id: Int?,
    val thumbnailUrl: String,
    val organizer: String,
    val title: String,
    val presenter: String,
    val date: String,
    val time: String,
    val youtubeLink: String?,
    val zoomLink: String?
)
