package com.endava.internship.mobile.budgetplanner.data.repository

import com.endava.internship.mobile.budgetplanner.data.model.BaseUser
import com.endava.internship.mobile.budgetplanner.data.model.LoggedUser
import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationInfo
import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationResponse
import com.endava.internship.mobile.budgetplanner.network.Resource

interface AuthRepository {
    suspend fun register(userRegistrationInfo: UserRegistrationInfo): Resource<UserRegistrationResponse>
    suspend fun validateUser(baseUser: BaseUser): Resource<Unit>
    suspend fun login(baseUser: BaseUser): Resource<LoggedUser>
    suspend fun logout(): Resource<Unit>
    suspend fun saveLoggedUser(loggedUser: LoggedUser): Unit
}
