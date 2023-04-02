package com.ihfazh.jadwal_ku.event.usecases.detail

import com.ihfazh.jadwal_ku.common.MediaUrlHelper
import com.ihfazh.jadwal_ku.common.converters.toDomain
import com.ihfazh.jadwal_ku.networking.KsatriaMuslimService
import retrofit2.HttpException
import java.io.IOException

class GetEventDetailUseCaseImpl(
    private val remote: KsatriaMuslimService,
    private val mediaUrlHelper: MediaUrlHelper,
): GetEventDetailUseCase {
    override suspend fun getDetail(id: String): GetEventDetailUseCase.Result {
        try {
            val resp = remote.getDetail(id)
            if (resp.event != null){
                return GetEventDetailUseCase.Result.Success(resp.event.toDomain(mediaUrlHelper))
            }
        } catch (e: IOException){
            return GetEventDetailUseCase.Result.NetworkError
        } catch (e: HttpException){
            return GetEventDetailUseCase.Result.GeneralError
        }

        return GetEventDetailUseCase.Result.GeneralError
    }
}