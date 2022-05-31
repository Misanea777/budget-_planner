package com.endava.internship.mobile.budgetplanner.ui.auth.register.onboarding.steps.two

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.data.model.Industry
import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationInfo
import com.endava.internship.mobile.budgetplanner.data.repository.AuthRepository
import com.endava.internship.mobile.budgetplanner.data.repository.IndustryRepository
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.providers.ResourceProvider
import com.endava.internship.mobile.budgetplanner.ui.base.BaseViewModel
import com.endava.internship.mobile.budgetplanner.util.*
import com.endava.internship.mobile.budgetplanner.util.validators.LiveDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingStepTwoViewModel @Inject constructor(
    val industryRepository: IndustryRepository,
    val authRepository: AuthRepository,
    val resourceProvider: ResourceProvider
) : BaseViewModel(resourceProvider) {

    var userRegistrationInfo = UserRegistrationInfo()

    private val _industries: MutableLiveData<ArrayList<Industry>> = MutableLiveData()
    val industries: LiveData<ArrayList<Industry>> = _industries

    private val _isSignedUp: MutableLiveData<Boolean> = MutableLiveData()
    val isSignedUp: LiveData<Boolean> = _isSignedUp

    val industry = MutableLiveData<Int>()

    val initialBalance: MutableLiveData<String> = MutableLiveData<String>()
    val initialBalanceValidator = LiveDataValidator(initialBalance).apply {
        addRule(resourceProvider.getStringRes(R.string.auth_onboarding_step_two_initial_balance_invalid_err)) {
            it?.isNotEmpty() ?: false
        }
        addRule(resourceProvider.getStringRes(R.string.auth_onboarding_step_two_initial_balance_invalid_err)) {
            it?.isLessOrEqualThan(Constants.MAX_INITIAL_BALANCE) ?: false
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

    private fun validateForm() {
        isOboardingStepTwoFormValidMediator.value =
            initialBalanceValidator.isValid() && industry.value != null
    }


    fun signUp() = asyncExecute {
        userRegistrationInfo.apply {
            industry = findIndustry()
            initialAmount = this@OnboardingStepTwoViewModel.initialBalance.value?.toDouble()
        }
        val result = authRepository.register(userRegistrationInfo)
        when (result) {
            is Resource.Success -> _isSignedUp.value = true
            is Resource.Failure -> pushStatusMessage(result.message)
        }
    }

    private fun findIndustry(): Industry? {
        val currentIndustries = industries.value
        val currentIndustry = industry.value
        return if (areNotNull(
                currentIndustries,
                currentIndustry
            )
        ) currentIndustry?.let { currentIndustries?.getOrNull(it) } else null
    }

    fun getIndustries() = asyncExecute {
        val response = industryRepository.getIndustries()
        when (response) {
            is Resource.Success -> _industries.value = response.value
            is Resource.Failure -> pushStatusMessage(response.message)
        }
    }

    private fun getIndustryPos(industry: Industry): Int? {
        val currentIndustries = industries.value
        return currentIndustries?.indices?.find { currentIndustries[it].industryId == industry.industryId }
    }

    fun setData(data: UserRegistrationInfo) {
        userRegistrationInfo = data
        userRegistrationInfo.industry?.let { industry.value = getIndustryPos(it) }
        userRegistrationInfo.initialAmount?.let { initialBalance.value = it.toString() }
    }

    fun saveData() {
        userRegistrationInfo.apply {
            industry = findIndustry()
            initialAmount = this@OnboardingStepTwoViewModel.initialBalance.value?.toDouble()
        }
    }
}
