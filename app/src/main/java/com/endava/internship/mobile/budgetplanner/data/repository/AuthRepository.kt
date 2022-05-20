package com.endava.internship.mobile.budgetplanner.data.repository

import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationInfo
import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationResponse
import com.endava.internship.mobile.budgetplanner.network.Resource

interface AuthRepository {
    suspend fun register(userRegistrationInfo: UserRegistrationInfo): Resource<UserRegistrationResponse>
}