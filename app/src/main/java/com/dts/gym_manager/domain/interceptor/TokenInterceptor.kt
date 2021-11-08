package com.dts.gym_manager.domain.interceptor

import com.dts.gym_manager.data.PrefsRepository
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val prefs: PrefsRepository) : Interceptor {

    companion object {
        private const val KEY_HEADER_AUTH = "Authorization"
        private const val TOKEN_BEARER_TYPE = "Bearer"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder().apply {
                if (prefs.isUserLoggedIn && prefs.token != null)
                    addHeader(KEY_HEADER_AUTH, "$TOKEN_BEARER_TYPE ${prefs.token?.token}")
            }.build()
        )
    }
}
