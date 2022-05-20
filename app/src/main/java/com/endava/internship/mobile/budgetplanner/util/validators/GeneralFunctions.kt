package com.endava.internship.mobile.budgetplanner.util.validators

import android.util.Patterns
import java.util.regex.Pattern

fun String.isValidUsername() = Pattern.matches("^[A-Za-z0-9.@]{8,30}$", this)

fun String.isValidFirstName() = Pattern.matches("^[A-Za-z]{3,22}$", this)
fun String.isValidLastName() = Pattern.matches("^[A-Za-z]{3,22}$", this)

fun String.isValidPassword() = Pattern.matches("^(?=.*[^A-Za-z0-9])(?=.*[a-zA-Z])(.{8,22})$", this)

fun String.hasMinimumOneSpecialChar() = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE).matcher(this).find()

fun String.hasMinimumOneAlphabeticChar() = Pattern.compile("[A-Za-z]", Pattern.CASE_INSENSITIVE).matcher(this).find()

fun String.lenInRange(range: IntRange) = this.length in range

fun areNotNull(vararg variables: Any?): Boolean =
    variables.fold(true) { isNotNull, variable -> isNotNull && variable != null }

fun String.isValidEmail() =
    Patterns.EMAIL_ADDRESS.matcher(this).matches()