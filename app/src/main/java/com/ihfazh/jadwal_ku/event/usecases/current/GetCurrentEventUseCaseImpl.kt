package com.ihfazh.jadwal_ku.event.usecases.current

import com.ihfazh.jadwal_ku.common.MediaUrlHelper
import com.ihfazh.jadwal_ku.event.Event
import com.ihfazh.jadwal_ku.event.EventLink
import com.ihfazh.jadwal_ku.event.usecases.current.GetCurrentEventUseCase.CurrentEventResponse
import com.ihfazh.jadwal_ku.networking.KsatriaMuslimService
import com.ihfazh.jadwal_ku.networking.schemas.EventSchema
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class GetCurrentEventUseCaseImpl(
    private val remote: KsatriaMuslimService,
    private val mediaUrlHelper: MediaUrlHelper
): GetCurrentEventUseCase {

    override suspend fun getCurrent(): CurrentEventResponse {
        return withContext(Dispatchers.IO){
            try {
                val resp = remote.getCurrentEvent()
                if (resp.event != null){
                    return@withContext resp.event.toDomain(mediaUrlHelper)
                }

            } catch (e: IOException){
                return@withContext CurrentEventResponse.NetworkError
            } catch (e: HttpException){
                return@withContext CurrentEventResponse.GeneralError
            }

            return@withContext CurrentEventResponse.EmptyEvent
        }
    }
}

private fun EventSchema.toDomain(mediaUrlHelper: MediaUrlHelper): CurrentEventResponse.Success {
    return CurrentEventResponse.Success(
        Event(
            id=id.toInt(),
            thumbnailUrl = mediaUrlHelper.prefixWithDomain(thumbnail),
            title = title,
            organizer = organizer,
            presenter = presenter,
            date = date,
            time = time,
            link = when{
                !youtubeLink.isNullOrEmpty() -> EventLink.YoutubeLink(youtubeLink)
                !zoomLink.isNullOrEmpty() -> EventLink.ZoomLink(zoomLink)
                else -> EventLink.EmptyLink
            }
        )
    )
}
