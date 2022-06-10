package com.endava.internship.mobile.budgetplanner.network

import com.endava.internship.mobile.budgetplanner.data.local.preferences.UserPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val userPreferences: UserPreferences,
    private val dispatcher: CoroutineDispatcher
) : Interceptor {

    private suspend fun getAuthToken(): String? = withContext(dispatcher) {
        return@withContext userPreferences.loggedUser.first()?.token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val authToken = runBlocking { getAuthToken() }
        return if (!authToken.isNullOrEmpty()) {
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $authToken")
                    .build()
            )
        } else chain.proceed(chain.request().newBuilder().build())
    }
}
