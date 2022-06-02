package com.endava.internship.mobile.budgetplanner.data.model

data class LoggedUser(
    val amount: Double,
    val firstName: String,
    val id: Int,
    val industry: Industry,
    val lastName: String,
    val token: String,
    val username: String
)