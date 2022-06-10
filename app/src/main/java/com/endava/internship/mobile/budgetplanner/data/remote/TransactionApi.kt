package com.endava.internship.mobile.budgetplanner.data.remote

import com.endava.internship.mobile.budgetplanner.data.model.ExpenseTransaction
import com.endava.internship.mobile.budgetplanner.data.model.IncomeTransaction
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

    @GET("${Constants.ApiPaths.INCOME_TRANSACTIONS_CATEGORY_PATH}/{category}")
    suspend fun getIncomeTransactionsCategory(@Path("category") category: String): TransactionsCategory

    @GET("${Constants.ApiPaths.EXPENSE_TRANSACTIONS_CATEGORY_PATH}/{category}")
    suspend fun getExpenseTransactionsCategory(@Path("category") category: String): TransactionsCategory

    @DELETE("${Constants.ApiPaths.TRANSACTION_INCOME_PATH}/{id}")
    suspend fun deleteIncomeTransaction(@Path("id") id: Int): Unit

    @DELETE("${Constants.ApiPaths.TRANSACTION_EXPENSE_PATH}/{id}")
    suspend fun deleteExpenseTransaction(@Path("id") id: Int): Unit
}
