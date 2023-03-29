package com.ihfazh.jadwal_ku.event


sealed interface EventLink {
    data class ZoomLink(val link: String): EventLink
    data class YoutubeLink(val link: String): EventLink
    object EmptyLink: EventLink
}

data class Event(
    val id: Int?,
    val thumbnailUrl: String,
    val organizer: String,
    val title: String,
    val presenter: String,
    val date: String,
    val time: String,
    val link: EventLink,
)
