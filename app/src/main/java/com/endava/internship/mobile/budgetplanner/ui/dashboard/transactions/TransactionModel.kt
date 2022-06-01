package com.endava.internship.mobile.budgetplanner.ui.dashboard.transactions

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import com.endava.internship.mobile.budgetplanner.R

class TransactionModel(val id: Int, val name: String, private val color: String) {
    val icon = R.drawable.ic_salaries_bonuses

    fun getBackgroundColor() = BlendModeColorFilter(Color.parseColor(color), BlendMode.SRC_ATOP)
}