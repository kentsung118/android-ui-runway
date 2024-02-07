package com.kent.android.slim.sample.websocket

import okhttp3.Interceptor
import okhttp3.Response

class BaseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
        val token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MDk4MDI0MTAsImp0aSI6IjEwM2U3YzEzLTUwYzUtNDFiNy1hYjk1LTU5ZDE1ZDAxOTE0YSIsImlhdCI6MTcwNzIxMDQxMCwic3ViIjoiYWNjZXNzIiwidXNlcklEIjoiM2UzM2Y2ODEtZTE5MS00YTBiLWFlNzQtYzIwMzc5ZmU0NjFmIn0.R6hbNIEn3bcAJf8vyeVrR2NVKcG1yL2tx7UNonlqaof4z8cwPBv1JKzzg6N4YUl7YwK-5SB1_fPijAOwwOpjDQ"
        if (token.isNotEmpty()) {
            builder.addHeader(
                "authorization",
                "Bearer $token"
            )
        }

        val request = builder.addHeader("Content-Type", "application/json")
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }
}