package com.endava.internship.mobile.budgetplanner.util.validators

import com.endava.internship.mobile.budgetplanner.BuildConfig

object Constants {
    const val MIN_FIRST_NAME_LEN = 3
    const val MAX_FIRST_NAME_LEN = 22
    const val MIN_LAST_NAME_LEN = 3
    const val MAX_LAST_NAME_LEN = 22
    const val MIN_USERNAME_LEN = 8
    const val MAX_USERNAME_LEN = 30
    const val MIN_PASSWORD_LEN = 8
    const val MAX_PASSWORD_LEN = 22

    const val AUTH_FRAGMENT_REQUEST_KEY = "auth_request_key"
    const val USER_REGISTRATION_INFO_KEY = "userRegistrationInfo"

    object ApiPaths {
        const val INDUSTRY_API_PATH: String = "industry"
        const val REGISTER_API_PATH: String = "registration"
    }

    const val BASE_URL_RETROFIT_API: String = BuildConfig.WEATHER_API_URL
}