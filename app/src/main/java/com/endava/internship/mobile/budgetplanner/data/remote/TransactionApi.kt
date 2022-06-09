package com.endava.internship.mobile.budgetplanner.data.remote

import com.endava.internship.mobile.budgetplanner.data.model.ExpenseTransaction
import com.endava.internship.mobile.budgetplanner.data.model.IncomeTransaction
import com.endava.internship.mobile.budgetplanner.util.Constants
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TransactionApi {

    @Headers("Content-Type: application/json")
    @POST(Constants.ApiPaths.ADD_TRANSACTION_INCOME_PATH)
    suspend fun addIncomeTransaction(@Body transaction: IncomeTransaction): IncomeTransaction

    @Headers("Content-Type: application/json")
    @POST(Constants.ApiPaths.ADD_TRANSACTION_EXPENSE_PATH)
    suspend fun addExpenseTransaction(@Body transaction: ExpenseTransaction): ExpenseTransaction
}