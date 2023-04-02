package com.ihfazh.jadwal_ku.event.usecases.current

import com.ihfazh.jadwal_ku.common.MediaUrlHelper
import com.ihfazh.jadwal_ku.common.converters.toDomain
import com.ihfazh.jadwal_ku.event.usecases.current.GetCurrentEventUseCase.CurrentEventResponse
import com.ihfazh.jadwal_ku.networking.KsatriaMuslimService
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
                    return@withContext CurrentEventResponse.Success(resp.event.toDomain(mediaUrlHelper))
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

