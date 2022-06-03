package com.endava.internship.mobile.budgetplanner.util

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
    const val MAX_INITIAL_BALANCE = 10000000
    const val MIN_INITIAL_BALANCE = 0.0

    const val NETWORK_ERROR_RESPONSE_MSG = "message"

    object ApiPaths {
        const val INDUSTRY_API_PATH: String = "industry"
        const val REGISTER_API_PATH: String = "registration"
        const val REGISTER_VALIDATE_PATH: String = "$REGISTER_API_PATH/validate"

        const val TRANSACTION_EXPENSE_CATEGORY_PATH: String = "expense"
        const val TRANSACTION_INCOME_CATEGORY_PATH: String = "income"

        const val CURRENT_BALANCE_PATH: String = "dashboard"
    }

    object DialogTags {
        const val SIGN_IN_LOADING: String = "SIGN_IN_LOADING"
        const val SIGN_IN_FAILED: String = "SIGN_IN_FAILED"
    }

    const val BASE_URL_RETROFIT_API: String = BuildConfig.WEATHER_API_URL
}
