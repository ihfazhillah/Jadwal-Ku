package com.ihfazh.jadwal_ku.networking.schemas

import com.squareup.moshi.Json

data class LoginResponseSchema(
    @field:Json(name = "token")
    val token: String,
)
