package com.ihfazh.jadwal_ku.networking

import com.ihfazh.jadwal_ku.networking.schemas.LoginBodySchema
import com.ihfazh.jadwal_ku.networking.schemas.LoginResponseSchema
import com.ihfazh.jadwal_ku.networking.schemas.SingleEventSchema
import com.ihfazh.jadwal_ku.networking.schemas.UpcomingEventsSchema
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface KsatriaMuslimService {
    @GET("/api/events/current/") suspend fun getCurrentEvent(): SingleEventSchema
    @GET("/api/events/upcoming/") suspend fun getUpcomingEvents(): UpcomingEventsSchema
    @GET("/api/events/{id}") suspend fun getDetail(@Path("id") id: String): SingleEventSchema

    @POST("/auth-token/") suspend fun login(@Body bodySchema: LoginBodySchema): LoginResponseSchema
}