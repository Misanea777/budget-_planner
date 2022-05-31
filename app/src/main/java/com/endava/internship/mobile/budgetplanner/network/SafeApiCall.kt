package com.endava.internship.mobile.budgetplanner.network

import com.endava.internship.mobile.budgetplanner.util.Constants
import com.endava.internship.mobile.budgetplanner.util.errors.RequestTimedOutException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException

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
                    val message: String? =
                        throwable.response()?.errorBody()?.charStream()?.readText()
                            ?.let { JSONObject(it).optString(Constants.NETWORK_ERROR_RESPONSE_MSG) }
                    Resource.Failure(
                        true,
                        throwable.code(),
                        throwable.response()?.errorBody(),
                        message
                    )
                }
                is SocketTimeoutException -> {
                    Resource.Failure(true, null, null, RequestTimedOutException().message)
                }
                else -> {
                    Resource.Failure(false, null, null, null)
                }
            }
        }
    }
}
