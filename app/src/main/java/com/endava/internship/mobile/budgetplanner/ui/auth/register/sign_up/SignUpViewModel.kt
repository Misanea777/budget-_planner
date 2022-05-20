package com.endava.internship.mobile.budgetplanner.ui.auth.register.sign_up

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
class SignUpViewModel @Inject constructor(
    @ApplicationContext context: Context,
) : ViewModel() {

    val userRegistrationInfo = UserRegistrationInfo()

    private val _isReadyToContinue: MutableLiveData<Boolean> = MutableLiveData()
    val isReadyToContinue: LiveData<Boolean> = _isReadyToContinue

    val username = MutableLiveData<String>()
    val usernameValidator = LiveDataValidator(username).apply {
        addRule(context.getString(R.string.auth_sign_up_username_len_err)) {
            it?.lenInRange(Constants.MIN_PASSWORD_LEN..Constants.MAX_PASSWORD_LEN) ?: false
        }
        addRule("Please enter a valid email address") {
            it?.isValidEmail() ?: false
        }
    }

    val password = MutableLiveData<String>()
    val passwordValidator = LiveDataValidator(password).apply {
        addRule(context.getString(R.string.auth_sign_up_password_len_err)) {
            it?.lenInRange(Constants.MIN_PASSWORD_LEN..Constants.MAX_PASSWORD_LEN) ?: false
        }
        addRule(context.getString(R.string.auth_sign_up_password_special_char_err)) {
         it?.hasMinimumOneSpecialChar() ?: false
        }
        addRule(context.getString(R.string.auth_sign_up_password_alphabetic_char_err)) {
            it?.hasMinimumOneAlphabeticChar() ?: false
        }
    }

    val confirmPassword = MutableLiveData<String>()
    val confirmPasswordValidator = LiveDataValidator(confirmPassword).apply {
        addRule(context.getString(R.string.auth_sign_up_confirm_password_err)) {
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

    fun continueSignUpUser() {
        userRegistrationInfo.apply {
            username = this@SignUpViewModel.username.value
            password = this@SignUpViewModel.password.value
        }

        _isReadyToContinue.value = true
    }

}