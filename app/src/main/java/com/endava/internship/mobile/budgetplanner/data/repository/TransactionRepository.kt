package com.endava.internship.mobile.budgetplanner.data.repository

import com.endava.internship.mobile.budgetplanner.data.model.ExpenseTransaction
import com.endava.internship.mobile.budgetplanner.data.model.IncomeTransaction
import com.endava.internship.mobile.budgetplanner.data.model.TransactionsCategory
import com.endava.internship.mobile.budgetplanner.network.Resource

interface TransactionRepository {
    suspend fun addIncomeTransaction(transaction: IncomeTransaction): Resource<IncomeTransaction>
    suspend fun addExpenseTransaction(transaction: ExpenseTransaction): Resource<ExpenseTransaction>
    suspend fun getTransactionsCategory(category: String, isExpenses: Boolean): Resource<TransactionsCategory>
    suspend fun deleteTransaction(id: Int, isExpense: Boolean): Resource<Unit>
}
