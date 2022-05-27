package com.endava.internship.mobile.budgetplanner.ui.auth.register.onboarding.steps.two

import androidx.lifecycle.*
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.data.model.Industry
import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationInfo
import com.endava.internship.mobile.budgetplanner.data.repository.AuthRepository
import com.endava.internship.mobile.budgetplanner.data.repository.IndustryRepository
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.providers.ResourceProvider
import com.endava.internship.mobile.budgetplanner.util.Constants
import com.endava.internship.mobile.budgetplanner.util.Event
import com.endava.internship.mobile.budgetplanner.util.areNotNull
import com.endava.internship.mobile.budgetplanner.util.isLessThan
import com.endava.internship.mobile.budgetplanner.util.validators.LiveDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OnboardingStepTwoViewModel @Inject constructor(
    val industryRepository: IndustryRepository,
    val authRepository: AuthRepository,
    val resourceProvider: ResourceProvider
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
        addRule(resourceProvider.getStringRes(R.string.auth_onboarding_step_two_initial_balance_empty_err)) {
            it?.isNotEmpty() ?: false
        }
        addRule(resourceProvider.getStringRes(R.string.auth_onboarding_step_two_initial_balance_too_big_err)) {
            it?.isLessThan(Constants.MAX_INITIAL_BALANCE) ?: false
        }
    }

    val isOboardingStepTwoFormValidMediator = MediatorLiveData<Boolean>()

    init {
        initialBalance.value = Constants.MIN_INITIAL_BALANCE.toString()
        _isSignedUp.value = false
        isOboardingStepTwoFormValidMediator.value = false
        isOboardingStepTwoFormValidMediator.addSource(industry) { validateForm() }
        isOboardingStepTwoFormValidMediator.addSource(initialBalance) { validateForm() }
    }

    private fun validateForm(
    ) {
        val currentIndustry = industry.value
        val isIndustrySelected = currentIndustry != null && currentIndustry > 0
        isOboardingStepTwoFormValidMediator.value =
            initialBalanceValidator.isValid() && isIndustrySelected
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
        val currentIndustries = industries.value
        val currentIndustry = industry.value?.minus(1)
        return if (areNotNull(
                currentIndustries,
                currentIndustry
            )
        ) currentIndustry?.let { currentIndustries?.getOrNull(it) } else null
    }

    fun getIndustries() = viewModelScope.launch {
        val response = industryRepository.getIndustries()
        when (response) {
            is Resource.Success -> _industries.value = response.value
            is Resource.Failure -> {
                if (response.isNetworkError) _statusMessage.value =
                    Event(resourceProvider.getStringRes(R.string.auth_onboarding_step_two_initial_balance_empty_err)) else _statusMessage.value =
                    Event(resourceProvider.getStringRes(R.string.auth_onboarding_step_two_initial_balance_too_big_err))
            }
        }
    }
}
