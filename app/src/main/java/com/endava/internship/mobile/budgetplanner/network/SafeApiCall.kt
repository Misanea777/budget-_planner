package com.endava.internship.mobile.budgetplanner.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): Resource<T> {
    return withContext(dispatcher) {
        try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    val message: String? = JSONObject(
                        throwable.response()?.errorBody()!!.charStream().readText()
                    ).optString("message")
                    Resource.Failure(
                        false,
                        throwable.code(),
                        throwable.response()?.errorBody(),
                        message
                    )
                }
                else -> {
                    Resource.Failure(true, null, null, null)
                }
            }
        }
    }
}