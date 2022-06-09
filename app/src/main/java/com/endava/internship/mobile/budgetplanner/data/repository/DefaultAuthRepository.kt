package com.endava.internship.mobile.budgetplanner.data.repository

import com.endava.internship.mobile.budgetplanner.data.local.preferences.UserPreferences
import com.endava.internship.mobile.budgetplanner.data.model.BaseUser
import com.endava.internship.mobile.budgetplanner.data.model.LoggedUser
import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationInfo
import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationResponse
import com.endava.internship.mobile.budgetplanner.data.remote.AuthApi
import com.endava.internship.mobile.budgetplanner.network.AuthorizationInterceptor
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher

class DefaultAuthRepository (
    private val api: AuthApi,
    private val ioDispatcher: CoroutineDispatcher,
    private val userPreferences: UserPreferences,
    private val authorizationInterceptor: AuthorizationInterceptor
) : AuthRepository {

    override suspend fun register(userRegistrationInfo: UserRegistrationInfo): Resource<UserRegistrationResponse> =
        safeApiCall(dispatcher = ioDispatcher) {
            api.register(userRegistrationInfo)
        }

    override suspend fun validateUser(baseUser: BaseUser): Resource<Unit> =
        safeApiCall(dispatcher = ioDispatcher) {
            api.validateUser(baseUser)
        }

    override suspend fun login(baseUser: BaseUser): Resource<LoggedUser> =
        safeApiCall(dispatcher = ioDispatcher) {
            api.login(baseUser)
        }

    override suspend fun logout(): Resource<Unit> =
        safeApiCall(dispatcher = ioDispatcher) {
            api.logout()
        }

    override suspend fun saveLoggedUser(loggedUser: LoggedUser) {
        userPreferences.saveLoggedUser(loggedUser)
        authorizationInterceptor.setAuthToken()
    }
}
