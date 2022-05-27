package com.endava.internship.mobile.budgetplanner.network

import com.endava.internship.mobile.budgetplanner.util.Constants
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
                    val message: String? = throwable.response()?.errorBody()?.charStream()?.readText()
                        ?.let { JSONObject(it).optString(Constants.NETWORK_ERROR_RESPONSE_MSG) }
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
