package com.ihfazh.jadwal_ku.networking

import com.ihfazh.jadwal_ku.screens.common.SettingsHelper
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val settingsHelper: SettingsHelper
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val path = request.url.encodedPath

        if (path != "/auth-token/"){
            val token = settingsHelper.getToken()
            if (token != null){
                val builder = request.newBuilder()
                builder.addHeader("Authorization", "Token $token")
                return chain.proceed(builder.build())
            }
        }

        return chain.proceed(request)
    }
}