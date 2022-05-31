package com.endava.internship.mobile.budgetplanner.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.providers.ResourceProvider
import com.endava.internship.mobile.budgetplanner.util.Event
import kotlinx.coroutines.launch

abstract class BaseViewModel (
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _statusMessage = MutableLiveData<Event<String>>()
    val statusMessage: LiveData<Event<String>> = _statusMessage

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    fun asyncExecute(func: suspend () -> Any?) = viewModelScope.launch {
        _isLoading.value = true
        func.invoke()
        _isLoading.value = false
    }

    fun pushStatusMessage(value: String?) {
        _statusMessage.value =
            value?.let { Event(it) } ?: Event(resourceProvider.getStringRes(R.string.error_unknown))
    }
}
