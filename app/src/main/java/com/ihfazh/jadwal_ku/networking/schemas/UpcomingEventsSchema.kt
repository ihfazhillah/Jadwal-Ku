package com.ihfazh.jadwal_ku.networking.schemas

import com.squareup.moshi.Json

data class UpcomingEventsSchema(
    @field:Json(name = "events")
    val events: List<EventListItemSchema>,

    @field:Json(name = "page")
    val page: Int = 1,

    @field:Json(name = "has_next")
    val hasNext: Boolean = false,
)
