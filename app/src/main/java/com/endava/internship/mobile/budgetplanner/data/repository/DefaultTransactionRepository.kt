package com.endava.internship.mobile.budgetplanner.data.repository

import com.endava.internship.mobile.budgetplanner.data.model.ExpenseTransaction
import com.endava.internship.mobile.budgetplanner.data.model.IncomeTransaction
import com.endava.internship.mobile.budgetplanner.data.remote.TransactionApi
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher

class DefaultTransactionRepository(
    private val api: TransactionApi,
    private  val ioDispatcher: CoroutineDispatcher,
) : TransactionRepository {

    override suspend fun addIncomeTransaction(transaction: IncomeTransaction): Resource<IncomeTransaction> =
        safeApiCall(dispatcher = ioDispatcher) {
            api.addIncomeTransaction(transaction)
        }

    override suspend fun addExpenseTransaction(transaction: ExpenseTransaction): Resource<ExpenseTransaction> =
        safeApiCall(dispatcher = ioDispatcher) {
            api.addExpenseTransaction(transaction)
        }
}
