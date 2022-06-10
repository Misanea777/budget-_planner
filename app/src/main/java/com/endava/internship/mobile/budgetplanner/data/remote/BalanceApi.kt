package com.endava.internship.mobile.budgetplanner.data.remote

import com.endava.internship.mobile.budgetplanner.data.model.Balance
import com.endava.internship.mobile.budgetplanner.util.Constants
import retrofit2.http.GET

interface BalanceApi {

    @GET(Constants.ApiPaths.BALANCE_CURRENT_AMOUNT_PATH)
    suspend fun getCurrentBalance(): Balance
}
