package com.endava.internship.mobile.budgetplanner.data.model

data class UserRegistrationResponse(
    val amount: Double,
    val firstName: String,
    val industry: Industry,
    val lastName: String,
    val userId: Int,
    val username: String
)