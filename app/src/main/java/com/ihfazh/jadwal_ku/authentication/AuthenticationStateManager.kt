package com.ihfazh.jadwal_ku.authentication

interface AuthenticationStateManager {
    suspend fun isLoggedIn(): Boolean
}