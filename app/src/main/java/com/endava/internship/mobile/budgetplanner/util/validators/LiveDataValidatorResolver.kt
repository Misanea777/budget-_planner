package com.endava.internship.mobile.budgetplanner.util.validators

class LiveDataValidatorResolver(private val validators: List<Pair<LiveDataValidator, Boolean>>) {
    fun isValid(): Boolean =
        validators.fold(true) { isValid, item -> item.first.isValid(item.second) && isValid }
}
