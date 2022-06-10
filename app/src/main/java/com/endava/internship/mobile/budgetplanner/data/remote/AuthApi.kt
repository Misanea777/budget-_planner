package com.endava.internship.mobile.budgetplanner.data.remote

import com.endava.internship.mobile.budgetplanner.data.model.BaseUser
import com.endava.internship.mobile.budgetplanner.data.model.LoggedUser
import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationInfo
import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationResponse
import com.endava.internship.mobile.budgetplanner.util.Constants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @Headers("Content-Type: application/json")
    @POST(Constants.ApiPaths.REGISTER_API_PATH)
    suspend fun register(@Body userRegistrationData: UserRegistrationInfo): UserRegistrationResponse

    @Headers("Content-Type: application/json")
    @POST(Constants.ApiPaths.REGISTER_VALIDATE_PATH)
    suspend fun validateUser(@Body baseUser: BaseUser): Unit

    @Headers("Content-Type: application/json")
    @POST(Constants.ApiPaths.AUTH_LOGIN_PATH)
    suspend fun login(@Body baseUser: BaseUser): LoggedUser

    @GET(Constants.ApiPaths.AUTH_LOGOUT_PATH)
    suspend fun logout(): Unit
}
