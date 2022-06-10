package com.endava.internship.mobile.budgetplanner.providers

import android.content.Context
import androidx.annotation.StringRes

class ResourceProvider(val context: Context) {

    fun getStringRes(@StringRes id: Int) = context.getString(id)
}
