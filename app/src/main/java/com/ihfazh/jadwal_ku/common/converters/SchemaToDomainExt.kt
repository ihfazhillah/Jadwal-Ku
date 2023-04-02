package com.ihfazh.jadwal_ku.common.converters

import com.ihfazh.jadwal_ku.common.MediaUrlHelper
import com.ihfazh.jadwal_ku.event.Event
import com.ihfazh.jadwal_ku.event.EventLink
import com.ihfazh.jadwal_ku.event.usecases.current.GetCurrentEventUseCase
import com.ihfazh.jadwal_ku.networking.schemas.EventSchema

fun EventSchema.toDomain(mediaUrlHelper: MediaUrlHelper): Event {
    return Event(
            id = id.toInt(),
            thumbnailUrl = mediaUrlHelper.prefixWithDomain(thumbnail),
            title = title,
            organizer = organizer,
            presenter = presenter,
            date = date,
            time = time,
            link = when {
                !youtubeLink.isNullOrEmpty() -> EventLink.YoutubeLink(youtubeLink)
                !zoomLink.isNullOrEmpty() -> EventLink.ZoomLink(zoomLink)
                else -> EventLink.EmptyLink
            }
        )
}