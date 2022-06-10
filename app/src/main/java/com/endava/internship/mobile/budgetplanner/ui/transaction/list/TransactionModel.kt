package com.endava.internship.mobile.budgetplanner.ui.transaction.list

import com.endava.internship.mobile.budgetplanner.util.asDollars
import com.endava.internship.mobile.budgetplanner.util.toTwoDecimalPlaces

class TransactionModel(
    val amount: Double,
    val date: String,
    val id: Int,
    val name: String,
    val note: String?,
    val onDelete: (position: Int, id: Int) -> Unit
) {
    fun getTransactionValue(): String = amount.toTwoDecimalPlaces().asDollars()
}