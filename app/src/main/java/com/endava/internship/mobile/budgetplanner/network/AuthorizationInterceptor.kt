package com.endava.internship.mobile.budgetplanner.network

import com.endava.internship.mobile.budgetplanner.data.local.preferences.UserPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(val userPreferences: UserPreferences, val dispatcher: CoroutineDispatcher) : Interceptor {

    var authToken: String? = null

    suspend fun setAuthToken() {
        withContext(dispatcher) {
            authToken = userPreferences.loggedUser.first()?.token
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return if(!authToken.isNullOrEmpty()) {
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $authToken")
                    .build()
            )
        } else chain.proceed(chain.request().newBuilder().build())
    }
}
