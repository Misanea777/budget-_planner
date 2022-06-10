package com.endava.internship.mobile.budgetplanner.data.model

data class CompleteTransaction(
    val amount: Double,
    val date: String,
    val id: Int,
    val name: String,
    val note: String?
)
