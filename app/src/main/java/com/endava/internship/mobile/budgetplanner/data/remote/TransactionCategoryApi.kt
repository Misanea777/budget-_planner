package com.endava.internship.mobile.budgetplanner.data.remote

import com.endava.internship.mobile.budgetplanner.data.model.ExpenseTransactionsGeneralInfo
import com.endava.internship.mobile.budgetplanner.data.model.IncomeTransactionsGeneralInfo
import com.endava.internship.mobile.budgetplanner.data.model.TransactionCategoryResponse
import com.endava.internship.mobile.budgetplanner.util.Constants
import retrofit2.http.GET

interface TransactionCategoryApi {

    @GET(Constants.ApiPaths.TRANSACTION_EXPENSE_CATEGORY_PATH)
    suspend fun getExpenseCategories(): TransactionCategoryResponse

    @GET(Constants.ApiPaths.TRANSACTION_INCOME_CATEGORY_PATH)
    suspend fun getIncomeCategories(): TransactionCategoryResponse

    @GET("dashboard/expense")
    suspend fun getExpenseTransactionsGeneralInfo(): ExpenseTransactionsGeneralInfo

    @GET("dashboard/income")
    suspend fun getIncomeTransactionsGeneralInfo(): IncomeTransactionsGeneralInfo
}
