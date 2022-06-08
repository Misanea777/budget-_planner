package com.endava.internship.mobile.budgetplanner.data.model

data class IncomeTransactionsGeneralInfo(
    val incomeCategories: Map<String, Long>,
    val sumIncomeCategories: Double
)