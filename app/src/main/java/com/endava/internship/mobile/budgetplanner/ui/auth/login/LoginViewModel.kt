package com.endava.internship.mobile.budgetplanner.ui.auth.login

import androidx.lifecycle.*
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.data.model.BaseUser
import com.endava.internship.mobile.budgetplanner.data.model.UserRegistrationInfo
import com.endava.internship.mobile.budgetplanner.data.repository.AuthRepository
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.providers.ResourceProvider
import com.endava.internship.mobile.budgetplanner.util.*
import com.endava.internship.mobile.budgetplanner.util.validators.LiveDataValidator
import com.endava.internship.mobile.budgetplanner.util.validators.LiveDataValidatorResolver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val authRepository: AuthRepository,
    val resourceProvider: ResourceProvider
) : ViewModel() {

    val statusMessage = MutableLiveData<String?>()

    val userRegistrationInfo = UserRegistrationInfo()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

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

    fun signInUser() = viewModelScope.launch {
        _isLoading.value = true
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
            is Resource.Failure -> {
                statusMessage.value = response.message
                    ?: resourceProvider.getStringRes(R.string.auth_sign_in_auth_request_timed_out_err)
            }
        }

        _isLoading.value = false
    }
}
