package com.endava.internship.mobile.budgetplanner.util.validators

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

typealias Predicate = (value: String?) -> Boolean

class LiveDataValidator(private val liveData: LiveData<String>) {
    private val validationRules = mutableListOf<Predicate>()
    private val errorMessages = mutableListOf< LiveData<String>>()


    var error = MutableLiveData<String?>()

    fun isValid(isEmitError: Boolean = true): Boolean {
        for (i in 0 until validationRules.size) {
            if (!validationRules[i](liveData.value)) {
                if (isEmitError) emitErrorMessage(errorMessages[i].value)
                return false
            }
        }

        if (isEmitError) emitErrorMessage(null)
        return true
    }

    private fun emitErrorMessage(messageRes: String?) {
        error.value = messageRes
    }

    fun addRule(errorMsg: String, predicate: Predicate) {
        val errMsgAsLiveData = MutableLiveData(errorMsg)
        validationRules.add(predicate)
        errorMessages.add(errMsgAsLiveData)
    }

    fun addRule(errorMsg: LiveData<String>, predicate: Predicate) {
        println("here")
        validationRules.add(predicate)
        errorMessages.add(errorMsg)
    }
}
