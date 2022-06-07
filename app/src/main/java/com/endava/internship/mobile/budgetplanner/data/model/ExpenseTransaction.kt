package com.endava.internship.mobile.budgetplanner.data.model

data class ExpenseTransaction(
    val amount: Double?,
    val date: String?,
    val expenseCategory: ExpenseCategory,
    val name: String?,
    val note: String?
)