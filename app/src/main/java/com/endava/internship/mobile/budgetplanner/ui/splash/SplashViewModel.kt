package com.endava.internship.mobile.budgetplanner.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

) : ViewModel() {
    private val MAX_DELAY_TIME = 1000L

    private val _isLoaded = MutableLiveData(false)
    val isLoaded: LiveData<Boolean> = _isLoaded

    fun load() = viewModelScope.launch {
        delay(MAX_DELAY_TIME)
        _isLoaded.value = true
    }
}
