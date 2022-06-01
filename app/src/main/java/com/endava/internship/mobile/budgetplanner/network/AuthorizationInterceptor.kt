package com.endava.internship.mobile.budgetplanner.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request().newBuilder()
            .addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaXNhbmVhQGdtYWlsLmNvbSIsImlkIjo4NTksImVtYWlsIjoibWlzYW5lYUBnbWFpbC5jb20iLCJmaXJzdE5hbWUiOiJkYXNkYXNkIiwibGFzdE5hbWUiOiJmYXNmYXNmYXMiLCJpYXQiOjE2NTQwNzc1NDR9.8qPrOkIFiSrLf6NPmgc-4ZVb_rSNa7gm9zpu07RwJtk")
            .build()
    )
}
