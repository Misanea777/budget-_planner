package com.endava.internship.mobile.budgetplanner.data.model

data class TransactionsCategory(
    val category: String,
    val sumOfTransactions: Double,
    val transactions: List<CompleteTransaction>
)