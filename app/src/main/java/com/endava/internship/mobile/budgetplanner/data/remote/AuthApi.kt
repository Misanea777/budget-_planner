package com.endava.internship.mobile.budgetplanner.data.remote

import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationInfo
import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationResponse
import com.endava.internship.mobile.budgetplanner.util.validators.Constants
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @Headers("Content-Type: application/json")
    @POST(Constants.ApiPaths.REGISTER_API_PATH)
    suspend fun register(@Body userRegistrationData: UserRegistrationInfo): UserRegistrationResponse
}