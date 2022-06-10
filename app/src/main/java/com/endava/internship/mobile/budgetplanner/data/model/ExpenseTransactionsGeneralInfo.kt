package com.endava.internship.mobile.budgetplanner.data.model

data class ExpenseTransactionsGeneralInfo(
    val expenseCategories: Map<String, Long>,
    val sumExpenseCategories: Double
)
