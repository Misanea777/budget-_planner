package com.endava.internship.mobile.budgetplanner.data.repository

import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationInfo
import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationResponse
import com.endava.internship.mobile.budgetplanner.data.remote.AuthApi
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher

class DefaultAuthRepository (
    private val api: AuthApi,
    private  val ioDispatcher: CoroutineDispatcher
) : AuthRepository {

    override suspend fun register(userRegistrationInfo: UserRegistrationInfo): Resource<UserRegistrationResponse> =
        safeApiCall(dispatcher = ioDispatcher) {
            println(userRegistrationInfo)
            api.register(userRegistrationInfo)
        }
}