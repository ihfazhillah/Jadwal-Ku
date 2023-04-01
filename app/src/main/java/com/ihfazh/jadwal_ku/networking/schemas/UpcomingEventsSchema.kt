package com.ihfazh.jadwal_ku.networking.schemas

import com.squareup.moshi.Json

data class UpcomingEventsSchema(
    @field:Json(name = "events")
    val events: List<EventListItemSchema>
)
