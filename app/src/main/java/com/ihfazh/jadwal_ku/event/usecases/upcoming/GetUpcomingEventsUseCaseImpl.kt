package com.ihfazh.jadwal_ku.event.usecases.upcoming

import com.ihfazh.jadwal_ku.common.MediaUrlHelper
import com.ihfazh.jadwal_ku.event.EventListItem
import com.ihfazh.jadwal_ku.event.usecases.upcoming.GetUpcomingEventsUseCase.*
import com.ihfazh.jadwal_ku.networking.KsatriaMuslimService
import com.ihfazh.jadwal_ku.networking.schemas.EventListItemSchema
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class GetUpcomingEventsUseCaseImpl(
    private val remoteService: KsatriaMuslimService,
    private val mediaUrlHelper: MediaUrlHelper
) : GetUpcomingEventsUseCase {

    override suspend fun getUpcomingEvents(limit: Int, page: Int): UpcomingEventsResponse {
        return withContext(Dispatchers.IO){
            try {
                val resp = remoteService.getUpcomingEvents(limit, page)
                if (resp.events.isNotEmpty()){
                    return@withContext UpcomingEventsResponse.Success(resp.events.toDomain(mediaUrlHelper), hasNext = resp.hasNext)
                }
            } catch (e: HttpException){
                return@withContext UpcomingEventsResponse.GeneralError
            } catch (e: IOException){
                return@withContext UpcomingEventsResponse.NetworkError
            }

            return@withContext UpcomingEventsResponse.EmptyEvent

        }
    }
}

private fun List<EventListItemSchema>.toDomain(mediaUrlHelper: MediaUrlHelper): List<EventListItem> {
    return map { eventListItemSchema ->
        EventListItem(
            eventListItemSchema.id,
            mediaUrlHelper.prefixWithDomain(eventListItemSchema.thumbnail),
            eventListItemSchema.title,
            eventListItemSchema.date,
            eventListItemSchema.time
        )
    }
}
