package com.endava.internship.mobile.budgetplanner.data.model

data class IncomeTransaction(
    val amount: Double?,
    val date: String?,
    val incomeCategory: IncomeCategory,
    val name: String?,
    val note: String?
)