package com.endava.internship.mobile.budgetplanner.data.repository

import com.endava.internship.mobile.budgetplanner.data.model.ExpenseTransactionsGeneralInfo
import com.endava.internship.mobile.budgetplanner.data.model.IncomeTransactionsGeneralInfo
import com.endava.internship.mobile.budgetplanner.data.model.TransactionCategoryResponse
import com.endava.internship.mobile.budgetplanner.network.Resource

interface TransactionCategoryRepository {
    suspend fun getExpenseCategories(): Resource<TransactionCategoryResponse>
    suspend fun getIncomeCategories(): Resource<TransactionCategoryResponse>
    suspend fun getExpenseTransactionsGeneralInfo(): Resource<ExpenseTransactionsGeneralInfo>
    suspend fun getIncomeTransactionsGeneralInfo(): Resource<IncomeTransactionsGeneralInfo>
}
