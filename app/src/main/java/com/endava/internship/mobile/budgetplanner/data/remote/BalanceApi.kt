package com.endava.internship.mobile.budgetplanner.data.remote

import com.endava.internship.mobile.budgetplanner.data.model.Balance
import com.endava.internship.mobile.budgetplanner.util.Constants
import retrofit2.http.GET

interface BalanceApi {

    @GET("dashboard/amount")
    suspend fun getCurrentBalance(): Balance
}
