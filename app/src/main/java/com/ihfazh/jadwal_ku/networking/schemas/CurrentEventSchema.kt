package com.ihfazh.jadwal_ku.networking.schemas

import com.squareup.moshi.Json

data class CurrentEventSchema(
    @field:Json(name = "event")
    val event: EventSchema?
)
