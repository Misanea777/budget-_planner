package com.endava.internship.mobile.budgetplanner.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserRegistrationInfo(
    var firstName: String? = null,
    var industry: Industry? = null,
    var initialAmount: Double? = null,
    var lastName: String? = null,
    var password: String? = null,
    var username: String? = null
) : Parcelable