package com.endava.internship.mobile.budgetplanner.ui.auth.register.onboarding.steps.two

import android.R.attr.data
import android.app.PendingIntent.getActivity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.data.model.Industry
import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationInfo
import com.endava.internship.mobile.budgetplanner.data.repository.AuthRepository
import com.endava.internship.mobile.budgetplanner.data.repository.IndustryRepository
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.util.validators.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OnboardingStepTwoViewModel @Inject constructor(
    val industryRepository: IndustryRepository,
    val authRepository: AuthRepository,
    @ApplicationContext val context: Context,
) : ViewModel() {

    private val _statusMessage = MutableLiveData<Event<String>>()
    val statusMessage: LiveData<Event<String>> = _statusMessage

    var userRegistrationInfo = UserRegistrationInfo()

    private val _industries: MutableLiveData<ArrayList<Industry>> = MutableLiveData()
    val industries: LiveData<ArrayList<Industry>> = _industries

    private val _isSignedUp: MutableLiveData<Boolean> = MutableLiveData()
    val isSignedUp: LiveData<Boolean> = _isSignedUp

    val industry = MutableLiveData<Int>()

    val initialBalance: MutableLiveData<String> = MutableLiveData<String>()
    val initialBalanceValidator = LiveDataValidator(initialBalance).apply {
        addRule("Initial Amount is invalid") {
            it?.isNotEmpty() ?: false
        }
        addRule("Initial Amount is invalid") {
            val asDouble = it?.toDoubleOrNull()
            asDouble != null && asDouble <= 10000000
        }
    }

    val isOboardingStepTwoFormValidMediator = MediatorLiveData<Boolean>()

    init {
        initialBalance.value = "0.0"
        _isSignedUp.value = false
        isOboardingStepTwoFormValidMediator.value = false
        isOboardingStepTwoFormValidMediator.addSource(industry) { validateForm() }
        isOboardingStepTwoFormValidMediator.addSource(initialBalance) { validateForm() }
    }

    private fun validateForm(
    ) {
        val currentIndustry = industry.value
        val isIndustrySelected = currentIndustry != null && currentIndustry > 0
        isOboardingStepTwoFormValidMediator.value = initialBalanceValidator.isValid() && isIndustrySelected
    }

    fun signUp() = viewModelScope.launch {
        userRegistrationInfo.apply {
            industry = findIndustry()
            initialAmount = this@OnboardingStepTwoViewModel.initialBalance.value?.toDouble()
        }
        val result = authRepository.register(userRegistrationInfo)
        when (result) {
            is Resource.Success -> _isSignedUp.value = true
            is Resource.Failure -> result.message?.let { _statusMessage.value = Event(it) }
        }
    }

    private fun findIndustry(): Industry? {
        val currentIndustires = industries.value
        val currentIndustry = industry.value
        return if (areNotNull(
                currentIndustires,
                currentIndustry
            )
        ) currentIndustires?.get(industry.value!!-1) else null
    }

    fun getIndustries() = viewModelScope.launch {
        val response = industryRepository.getIndustries()
        when (response) {
            is Resource.Success -> _industries.value = response.value
            is Resource.Failure -> {
                if (response.isNetworkError) _statusMessage.value =
                    Event("No internet connection!") else _statusMessage.value =
                    Event("Something went wrong!")
            }
        }
    }
}