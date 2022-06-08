package com.endava.internship.mobile.budgetplanner.ui.dashboard.transactions

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import com.endava.internship.mobile.budgetplanner.util.categoryExpensesIDToResourceID
import com.endava.internship.mobile.budgetplanner.util.categoryIncomeIDToResourceID

class TransactionModel(
    val id: Int,
    val name: String,
    private val color: String,
    isExpenses: Boolean
) {
    val icon = if(isExpenses) categoryExpensesIDToResourceID(id) else categoryIncomeIDToResourceID(id)

    fun getBackgroundColor() = BlendModeColorFilter(Color.parseColor(color), BlendMode.SRC_ATOP)

    var numberOfTransactions: Long = 0

    fun getNumberOfTransactions() = "$numberOfTransactions transactions"
}
