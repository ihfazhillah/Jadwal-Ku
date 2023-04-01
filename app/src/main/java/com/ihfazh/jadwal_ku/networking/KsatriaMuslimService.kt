package com.ihfazh.jadwal_ku.networking

import com.ihfazh.jadwal_ku.networking.schemas.CurrentEventSchema
import retrofit2.http.GET

interface KsatriaMuslimService {
    @GET("/api/events/current/") suspend fun getCurrentEvent(): CurrentEventSchema
    @GET("/api/events/upcoming/") suspend fun getUpcomingEvents()
}