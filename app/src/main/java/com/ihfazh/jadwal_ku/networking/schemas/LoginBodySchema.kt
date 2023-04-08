package com.ihfazh.jadwal_ku.networking.schemas

import com.squareup.moshi.Json

data class LoginBodySchema(
    @field:Json(name = "username")
    val username: String,
    @field:Json(name = "password")
    val password: String
)
