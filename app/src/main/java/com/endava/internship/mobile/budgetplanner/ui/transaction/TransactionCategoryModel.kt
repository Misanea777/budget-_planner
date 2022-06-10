package com.endava.internship.mobile.budgetplanner.ui.transaction

import android.R
import android.content.res.ColorStateList
import android.graphics.Color

class TransactionCategoryModel(
    val id: Int,
    val name: String,
    val color: String,
) {

    val backgroundColorStateList = ColorStateList(
        arrayOf(
            intArrayOf(R.attr.state_enabled)
        ),
        intArrayOf(
            Color.parseColor(color),
        )
    )
}