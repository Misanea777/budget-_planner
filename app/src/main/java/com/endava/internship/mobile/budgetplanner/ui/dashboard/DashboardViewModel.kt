package com.endava.internship.mobile.budgetplanner.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.endava.internship.mobile.budgetplanner.data.repository.AuthRepository
import com.endava.internship.mobile.budgetplanner.data.repository.BalanceRepository
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.providers.ResourceProvider
import com.endava.internship.mobile.budgetplanner.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    val balanceRepository: BalanceRepository,
    val authRepository: AuthRepository,
    val resourceProvider: ResourceProvider
) : BaseViewModel(resourceProvider) {

    private val _balance: MutableLiveData<Double> = MutableLiveData()
    val balance: LiveData<Double> = _balance

    private val _isLoggedOut: MutableLiveData<Boolean> = MutableLiveData()
    val isLoggedOut: LiveData<Boolean> = _isLoggedOut

    fun getCurrentBalance() = asyncExecute {
        val response = balanceRepository.getCurrentBalance()
        when (response) {
            is Resource.Success -> _balance.value = response.value.amount
            is Resource.Failure -> pushStatusMessage(response.message)
        }
    }

    fun signOut() = asyncExecute {
        val response = authRepository.logout()
        when (response) {
            is Resource.Success -> _isLoggedOut.value = true
            is Resource.Failure -> pushStatusMessage(response.message)
        }
    }
}
