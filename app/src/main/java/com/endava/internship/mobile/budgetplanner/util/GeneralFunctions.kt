package com.endava.internship.mobile.budgetplanner.util

import android.util.Patterns
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.regex.Pattern
import kotlin.math.floor
import kotlin.math.roundToInt

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

fun String.isLessOrEqualThan(number: Int): Boolean {
    val asDouble = this.toDoubleOrNull()
    return asDouble != null && asDouble <= number
}

val formatter = DecimalFormat("##0.00").apply {
    roundingMode = RoundingMode.DOWN
}

fun Double.toFancyNumberFormat(): String = when(floor(this).toInt()) {
    in 0..999 -> formatter.format(this)
    in 1000..999999 -> "${formatter.format(this / 1000)}k"
    in 1000000..Int.MAX_VALUE -> "${formatter.format(this / 1000000)}m"
    else -> this.toString()
}

fun String.asDollars(): String = "$$this"


