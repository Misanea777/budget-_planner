package com.endava.internship.mobile.budgetplanner.util.validators

class LiveDataStepValidatorResolver(private val validators: List<LiveDataValidator>) {
    fun isValid(): Boolean = validators.fold(true) { isValid, item -> isValid && item.isValid() }
}
