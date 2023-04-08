package com.ihfazh.jadwal_ku.authentication

class DummyLoginUseCase : LoginUseCase {
    override suspend fun login(username: String, password: String): LoginUseCase.Result {
        TODO("Not yet implemented")
    }
}