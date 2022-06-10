package com.endava.internship.mobile.budgetplanner.data.remote

import com.endava.internship.mobile.budgetplanner.data.model.ExpenseTransaction
import com.endava.internship.mobile.budgetplanner.data.model.IncomeTransaction
import com.endava.internship.mobile.budgetplanner.data.model.IndustryResponse
import com.endava.internship.mobile.budgetplanner.data.model.TransactionsCategory
import com.endava.internship.mobile.budgetplanner.util.Constants
import retrofit2.http.*

interface TransactionApi {

    @Headers("Content-Type: application/json")
    @POST(Constants.ApiPaths.ADD_TRANSACTION_INCOME_PATH)
    suspend fun addIncomeTransaction(@Body transaction: IncomeTransaction): IncomeTransaction

    @Headers("Content-Type: application/json")
    @POST(Constants.ApiPaths.ADD_TRANSACTION_EXPENSE_PATH)
    suspend fun addExpenseTransaction(@Body transaction: ExpenseTransaction): ExpenseTransaction

    @GET("dashboard/income/{category}")
    suspend fun getIncomeTransactionsCategory(@Path("category") category: String): TransactionsCategory

    @GET("dashboard/expense/{category}")
    suspend fun getExpenseTransactionsCategory(@Path("category") category: String): TransactionsCategory

    @DELETE("transaction/income/{id}")
    suspend fun deleteIncomeTransaction(@Path("id") id: Int): Unit

    @DELETE("transaction/expense/{id}")
    suspend fun deleteExpenseTransaction(@Path("id") id: Int): Unit
}
