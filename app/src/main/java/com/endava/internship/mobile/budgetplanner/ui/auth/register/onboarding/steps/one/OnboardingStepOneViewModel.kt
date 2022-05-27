package com.endava.internship.mobile.budgetplanner.ui.auth.register.onboarding.steps.one

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationInfo
import com.endava.internship.mobile.budgetplanner.providers.ResourceProvider
import com.endava.internship.mobile.budgetplanner.util.Constants
import com.endava.internship.mobile.budgetplanner.util.isValidFirstName
import com.endava.internship.mobile.budgetplanner.util.isValidLastName
import com.endava.internship.mobile.budgetplanner.util.lenInRange
import com.endava.internship.mobile.budgetplanner.util.validators.LiveDataValidator
import com.endava.internship.mobile.budgetplanner.util.validators.LiveDataValidatorResolver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingStepOneViewModel @Inject constructor(
    val resourceProvider: ResourceProvider
) : ViewModel() {

    var userRegistrationInfo = UserRegistrationInfo()

    private val _isReadyToContinueToStepTwo: MutableLiveData<Boolean> = MutableLiveData()
    val isReadyToContinueToStepTwo: LiveData<Boolean> = _isReadyToContinueToStepTwo

    val firstName = MutableLiveData<String>()
    val firstNameValidator = LiveDataValidator(firstName).apply {
        addRule(resourceProvider.getStringRes(R.string.auth_onboarding_step_one_first_name_len_err)) {
            it?.lenInRange(Constants.MIN_FIRST_NAME_LEN..Constants.MAX_FIRST_NAME_LEN) ?: false
        }
        addRule(resourceProvider.getStringRes(R.string.auth_onboarding_step_one_first_name_alpha_err)) {
            it?.isValidFirstName() ?: false
        }
    }

    val lastName = MutableLiveData<String>()
    val lastNameValidator = LiveDataValidator(lastName).apply {
        addRule(resourceProvider.getStringRes(R.string.auth_onboarding_step_one_last_name_len_err)) {
            it?.lenInRange(Constants.MIN_LAST_NAME_LEN..Constants.MAX_LAST_NAME_LEN) ?: false
        }
        addRule(resourceProvider.getStringRes(R.string.auth_onboarding_step_one_last_name_alpha_err)) {
            it?.isValidLastName() ?: false
        }
    }

    val isOboardingStepOneFormValidMediator = MediatorLiveData<Boolean>()

    init {
        _isReadyToContinueToStepTwo.value = false
        isOboardingStepOneFormValidMediator.value = false
        isOboardingStepOneFormValidMediator.addSource(firstName) { validateForm(isUsernameChanged = true) }
        isOboardingStepOneFormValidMediator.addSource(lastName) { validateForm(isPasswordChanged = true) }
    }

    private fun validateForm(
        isUsernameChanged: Boolean = false,
        isPasswordChanged: Boolean = false,
    ) {
        val validators = listOf(
            Pair(firstNameValidator, isUsernameChanged),
            Pair(lastNameValidator, isPasswordChanged),
        )
        val validatorResolver = LiveDataValidatorResolver(validators)
        isOboardingStepOneFormValidMediator.value = validatorResolver.isValid()
    }

    fun continueToStepTwo() {
        userRegistrationInfo.apply {
            firstName = this@OnboardingStepOneViewModel.firstName.value
            lastName = this@OnboardingStepOneViewModel.lastName.value
        }

        _isReadyToContinueToStepTwo.value = true
    }
}
