package com.endava.internship.mobile.budgetplanner.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Industry(
    val industryId: Int,
    val name: String
) : Parcelable