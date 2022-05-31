package com.endava.internship.mobile.budgetplanner.ui.auth.register.sign_up

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
class SignUpViewModel @Inject constructor(
    val authRepository: AuthRepository,
    val resourceProvider: ResourceProvider
) : BaseViewModel(resourceProvider) {

    var userRegistrationInfo = UserRegistrationInfo()

    private val _isReadyToContinue: MutableLiveData<Boolean> = MutableLiveData()
    val isReadyToContinue: LiveData<Boolean> = _isReadyToContinue

    val username = MutableLiveData<String>()
    val usernameValidator = LiveDataValidator(username).apply {
        addRule(resourceProvider.getStringRes(R.string.auth_sign_up_username_len_err)) {
            it?.lenInRange(Constants.MIN_PASSWORD_LEN..Constants.MAX_PASSWORD_LEN) ?: false
        }
        addRule(resourceProvider.getStringRes(R.string.auth_sign_up_username_email_err)) {
            it?.isValidEmail() ?: false
        }
    }

    val password = MutableLiveData<String>()
    val passwordValidator = LiveDataValidator(password).apply {
        addRule(resourceProvider.getStringRes(R.string.auth_sign_up_password_len_err)) {
            it?.lenInRange(Constants.MIN_PASSWORD_LEN..Constants.MAX_PASSWORD_LEN) ?: false
        }
        addRule(resourceProvider.getStringRes(R.string.auth_sign_up_password_special_char_err)) {
            it?.hasMinimumOneSpecialChar() ?: false
        }
        addRule(resourceProvider.getStringRes(R.string.auth_sign_up_password_alphabetic_char_err)) {
            it?.hasMinimumOneAlphabeticChar() ?: false
        }
    }

    val confirmPassword = MutableLiveData<String>()
    val confirmPasswordValidator = LiveDataValidator(confirmPassword).apply {
        addRule(resourceProvider.getStringRes(R.string.auth_sign_up_confirm_password_err)) {
            if (it == null) false else it == password.value
        }
    }

    val isSignUpFormValidMediator = MediatorLiveData<Boolean>()

    init {
        _isReadyToContinue.value = false
        isSignUpFormValidMediator.value = false
        isSignUpFormValidMediator.addSource(username) { validateForm(isUsernameChanged = true) }
        isSignUpFormValidMediator.addSource(password) { validateForm(isPasswordChanged = true) }
        isSignUpFormValidMediator.addSource(confirmPassword) { validateForm(isConfirmPasswordChanged = true) }
    }

    private fun validateForm(
        isUsernameChanged: Boolean = false,
        isPasswordChanged: Boolean = false,
        isConfirmPasswordChanged: Boolean = false
    ) {
        val validators = listOf(
            Pair(usernameValidator, isUsernameChanged),
            Pair(passwordValidator, isPasswordChanged),
            Pair(confirmPasswordValidator, isConfirmPasswordChanged)
        )
        val validatorResolver = LiveDataValidatorResolver(validators)
        isSignUpFormValidMediator.value = validatorResolver.isValid()
    }

    fun continueSignUpUser() = asyncExecute {
        saveData()

        val result = authRepository.validateUser(
            BaseUser(
                userRegistrationInfo.password,
                userRegistrationInfo.username
            )
        )

        when(result) {
            is Resource.Success -> _isReadyToContinue.value = true
            is Resource.Failure -> pushStatusMessage(result.message)
        }
    }

    fun setData(data: UserRegistrationInfo) {
        userRegistrationInfo = data
        userRegistrationInfo.username?.let { username.value = it }
        userRegistrationInfo.password?.let {
            password.value = it
            confirmPassword.value = it
        }
    }

    fun saveData() {
        userRegistrationInfo.apply {
            username = this@SignUpViewModel.username.value
            password = this@SignUpViewModel.password.value
        }
    }
}
