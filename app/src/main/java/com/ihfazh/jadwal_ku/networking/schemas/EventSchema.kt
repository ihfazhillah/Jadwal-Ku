package com.ihfazh.jadwal_ku.networking.schemas

import com.squareup.moshi.Json

data class EventSchema(
    @field:Json(name = "id")
    val id: String,

    @field:Json(name = "title")
    val title: String,

    @field:Json(name = "thumbnail")
    val thumbnail: String,

    @field:Json(name = "organizer")
    val organizer: String,

    @field:Json(name = "presenter")
    val presenter: String,

    @field:Json(name = "date")
    val date: String,

    @field:Json(name = "time")
    val time: String,

    @field:Json(name = "youtube_link")
    val youtubeLink: String? = null,

    @field:Json(name = "zoom_link")
    val zoomLink: String? = null
)
