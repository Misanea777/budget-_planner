package com.endava.internship.mobile.budgetplanner.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.data.model.BaseUser
import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationInfo
import com.endava.internship.mobile.budgetplanner.data.repository.AuthRepository
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.providers.ResourceProvider
import com.endava.internship.mobile.budgetplanner.ui.base.BaseViewModel
import com.endava.internship.mobile.budgetplanner.util.*
import com.endava.internship.mobile.budgetplanner.util.validators.LiveDataValidator
import com.endava.internship.mobile.budgetplanner.util.validators.LiveDataValidatorResolver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val authRepository: AuthRepository,
    val resourceProvider: ResourceProvider
) : BaseViewModel(resourceProvider) {

    val userRegistrationInfo = UserRegistrationInfo()

    private val _isSignedIn: MutableLiveData<Boolean> = MutableLiveData()
    val isSignedIn: LiveData<Boolean> = _isSignedIn

    val username = MutableLiveData<String>()
    val usernameValidator = LiveDataValidator(username).apply {
        addRule(resourceProvider.getStringRes(R.string.auth_sign_in_invalid_credentials_err)) {
            it?.lenInRange(Constants.MIN_PASSWORD_LEN..Constants.MAX_PASSWORD_LEN) ?: false
        }
        addRule(resourceProvider.getStringRes(R.string.auth_sign_in_invalid_credentials_err)) {
            it?.isValidEmail() ?: false
        }
    }

    val password = MutableLiveData<String>()
    val passwordValidator = LiveDataValidator(password).apply {
        addRule(resourceProvider.getStringRes(R.string.auth_sign_in_invalid_credentials_err)) {
            it?.lenInRange(Constants.MIN_PASSWORD_LEN..Constants.MAX_PASSWORD_LEN) ?: false
        }
        addRule(resourceProvider.getStringRes(R.string.auth_sign_in_invalid_credentials_err)) {
            it?.hasMinimumOneSpecialChar() ?: false
        }
        addRule(resourceProvider.getStringRes(R.string.auth_sign_in_invalid_credentials_err)) {
            it?.hasMinimumOneAlphabeticChar() ?: false
        }
    }

    val isSignUpFormValidMediator = MediatorLiveData<Boolean>()

    init {
        _isSignedIn.value = false
        isSignUpFormValidMediator.value = false
        isSignUpFormValidMediator.addSource(username) { validateForm(isUsernameChanged = true) }
        isSignUpFormValidMediator.addSource(password) { validateForm(isPasswordChanged = true) }
    }

    private fun validateForm(
        isUsernameChanged: Boolean = false,
        isPasswordChanged: Boolean = false,
    ) {
        val validators = listOf(
            Pair(usernameValidator, isUsernameChanged),
            Pair(passwordValidator, isPasswordChanged),
        )
        val validatorResolver = LiveDataValidatorResolver(validators)
        isSignUpFormValidMediator.value = validatorResolver.isValid()
    }

    fun signInUser() = asyncExecute {
        userRegistrationInfo.apply {
            username = this@LoginViewModel.username.value
            password = this@LoginViewModel.password.value
        }

        val response = authRepository.login(
            BaseUser(
                userRegistrationInfo.password,
                userRegistrationInfo.username
            )
        )

        when (response) {
            is Resource.Success -> _isSignedIn.value = true
            is Resource.Failure -> pushStatusMessage(response.message)
        }
    }
}
