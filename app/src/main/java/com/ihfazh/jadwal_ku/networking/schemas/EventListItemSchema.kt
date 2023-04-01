package com.ihfazh.jadwal_ku.networking.schemas

import com.squareup.moshi.Json

data class EventListItemSchema(
    @field:Json(name = "id")
    val id: String,

    @field:Json(name = "title")
    val title: String,

    @field:Json(name = "thumbnail")
    val thumbnail: String,


    @field:Json(name = "date")
    val date: String,

    @field:Json(name = "time")
    val time: String,
)
