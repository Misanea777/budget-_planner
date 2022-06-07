package com.endava.internship.mobile.budgetplanner.data.remote

import com.endava.internship.mobile.budgetplanner.data.model.ExpenseTransaction
import com.endava.internship.mobile.budgetplanner.data.model.IncomeTransaction
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TransactionApi {

    @Headers("Content-Type: application/json")
    @POST("transaction/income")
    suspend fun addIncomeTransaction(@Body transaction: IncomeTransaction): IncomeTransaction

    @Headers("Content-Type: application/json")
    @POST("transaction/expense")
    suspend fun addExpenseTransaction(@Body transaction: ExpenseTransaction): ExpenseTransaction
}