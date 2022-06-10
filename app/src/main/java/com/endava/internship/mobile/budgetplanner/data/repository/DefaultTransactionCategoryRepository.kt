package com.endava.internship.mobile.budgetplanner.data.repository

import com.endava.internship.mobile.budgetplanner.data.model.ExpenseTransactionsGeneralInfo
import com.endava.internship.mobile.budgetplanner.data.model.IncomeTransactionsGeneralInfo
import com.endava.internship.mobile.budgetplanner.data.model.TransactionCategoryResponse
import com.endava.internship.mobile.budgetplanner.data.remote.TransactionCategoryApi
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher

class DefaultTransactionCategoryRepository (
    private val api: TransactionCategoryApi,
    private  val ioDispatcher: CoroutineDispatcher
) : TransactionCategoryRepository {
    override suspend fun getExpenseCategories(): Resource<TransactionCategoryResponse> = safeApiCall(dispatcher = ioDispatcher) {
        api.getExpenseCategories()
    }

    override suspend fun getIncomeCategories(): Resource<TransactionCategoryResponse> = safeApiCall(dispatcher = ioDispatcher) {
        api.getIncomeCategories()
    }

    override suspend fun getExpenseTransactionsGeneralInfo(): Resource<ExpenseTransactionsGeneralInfo> = safeApiCall(dispatcher = ioDispatcher) {
        api.getExpenseTransactionsGeneralInfo()
    }

    override suspend fun getIncomeTransactionsGeneralInfo(): Resource<IncomeTransactionsGeneralInfo> = safeApiCall(dispatcher = ioDispatcher) {
        api.getIncomeTransactionsGeneralInfo()
    }
}
