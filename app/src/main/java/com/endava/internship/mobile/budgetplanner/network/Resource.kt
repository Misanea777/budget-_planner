package com.endava.internship.mobile.budgetplanner.network

import okhttp3.ResponseBody

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?,
        val message: String?
    ) : Resource<Nothing>()
}
