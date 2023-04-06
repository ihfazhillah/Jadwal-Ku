package com.ihfazh.jadwal_ku.authentication

import kotlinx.coroutines.delay

class DummyAuthenticationStateManager: AuthenticationStateManager {
    override suspend fun isLoggedIn(): Boolean {
        delay(500)
        return false
    }
}