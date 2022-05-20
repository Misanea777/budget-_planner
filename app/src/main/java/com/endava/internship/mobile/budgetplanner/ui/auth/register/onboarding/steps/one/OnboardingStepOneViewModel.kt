package com.endava.internship.mobile.budgetplanner.ui.auth.register.onboarding.steps.one

import android.content.Context
import androidx.lifecycle.*
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationInfo
import com.endava.internship.mobile.budgetplanner.util.validators.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingStepOneViewModel @Inject constructor(
    @ApplicationContext context: Context,
) : ViewModel() {

    var userRegistrationInfo = UserRegistrationInfo()

    private val _isReadyToContinueToStepTwo: MutableLiveData<Boolean> = MutableLiveData()
    val isReadyToContinueToStepTwo: LiveData<Boolean> = _isReadyToContinueToStepTwo

    val firstName = MutableLiveData<String>()
    val firstNameValidator = LiveDataValidator(firstName).apply {
        addRule("The First name must be greater than 3 or equal to 22") {
            it?.lenInRange(Constants.MIN_FIRST_NAME_LEN..Constants.MAX_FIRST_NAME_LEN) ?: false
        }
        addRule("First name should contain only alpha characters") {
            it?.isValidFirstName() ?: false
        }
    }

    val lastName = MutableLiveData<String>()
    val lastNameValidator = LiveDataValidator(lastName).apply {
        addRule("The Last name must be greater than 3 or equal to 22") {
            it?.lenInRange(Constants.MIN_LAST_NAME_LEN..Constants.MAX_LAST_NAME_LEN) ?: false
        }
        addRule("Last name should contain only alpha characters") {
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