package com.endava.internship.mobile.budgetplanner.data.repository

import com.endava.internship.mobile.budgetplanner.data.model.TransactionCategoryResponse
import com.endava.internship.mobile.budgetplanner.network.Resource

interface TransactionCategoryRepository {
    suspend fun getExpenseCategories(): Resource<TransactionCategoryResponse>
    suspend fun getIncomeCategories(): Resource<TransactionCategoryResponse>
}
