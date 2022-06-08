package com.endava.internship.mobile.budgetplanner.util

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.core.view.children
import com.endava.internship.mobile.budgetplanner.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.math.floor
import kotlin.math.roundToInt

fun String.isValidUsername() = Pattern.matches("^[A-Za-z0-9.@]{8,30}$", this)

fun String.isValidFirstName() = Pattern.matches("^[A-Za-z]{3,22}$", this)
fun String.isValidLastName() = Pattern.matches("^[A-Za-z]{3,22}$", this)

fun String.containsOnlyAlphaChar() = Pattern.matches("^[A-Za-z]+$", this)

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

fun String.isLessOrEqualThan(number: Double?): Boolean {
    val asDouble = this.toDoubleOrNull()
    return asDouble != null && number != null && asDouble <= number
}

fun String.isGreaterOrEqualThan(number: Int): Boolean {
    val asDouble = this.toDoubleOrNull()
    return asDouble != null && asDouble >= number
}

val formatter = DecimalFormat("##0.00").apply {
    roundingMode = RoundingMode.DOWN
}

fun Double.toTwoDecimalPlaces(): String = formatter.format(this)

fun Double.toFancyNumberFormat(): String = when(floor(this).toInt()) {
    in 0..999 -> formatter.format(this)
    in 1000..999999 -> "${formatter.format(this / 1000)}k"
    in 1000000..Int.MAX_VALUE -> "${formatter.format(this / 1000000)}m"
    else -> this.toString()
}

fun Double.toMinimalisticNumberFormat(): String = when(floor(this).toInt()) {
    in 0..999 -> "Less than 1k"
    in 1000..1000 -> "1k"
    in 1001..4999 -> "Less than 5k"
    in 5000..5000 -> "5k"
    in 5001..Int.MAX_VALUE -> "More than 5k"
    else -> Double.toString()
}

fun String.asDollars(): String = "$$this"
fun String.minusInFront(): String = "-$this"
fun String.plusInFront(): String = "+$this"

fun categoryExpensesIDToResourceID(id: Int): Int {
    return when (id) {
        1 -> R.drawable.ic_food_drink
        2 -> R.drawable.ic_entertainment
        3 -> R.drawable.ic_taxes_payments
        4 -> R.drawable.ic_shopping
        5 -> R.drawable.ic_sports
        6 -> R.drawable.ic_others
        else -> R.drawable.ic_others
    }
}

fun categoryIncomeIDToResourceID(id: Int): Int {
    return when (id) {
        1 -> R.drawable.ic_salaries_bonuses
        2 -> R.drawable.ic_investments
        3 -> R.drawable.ic_payments_transfers
        4 -> R.drawable.ic_others
        else -> R.drawable.ic_others
    }
}

fun TextInputEditText.restrictWithoutSpaces() {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

        override fun afterTextChanged(p0: Editable?) {
            val textEntered = this@restrictWithoutSpaces.text.toString()

            if (textEntered.isNotEmpty() && textEntered.contains(" ")) {
                this@restrictWithoutSpaces.setText(this@restrictWithoutSpaces.text.toString().replace(" ", ""));
                this@restrictWithoutSpaces.setSelection(this@restrictWithoutSpaces.text!!.length);
            }
        }})
}

fun ChipGroup.setUncheckedChipsAlpha(checkedIds: List<Int>, alpha: Float) {
    this.children.filter { !checkedIds.contains(it.id) }.forEach { it.alpha = alpha }
    this.children.filter { checkedIds.contains(it.id) }.forEach { it.alpha = 1f }
}

fun getCalendarInstanceFromUTC() = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

fun Calendar.todayTime() = this.apply {
    timeInMillis = MaterialDatePicker.todayInUtcMilliseconds()
}
fun Calendar.oneYearAgoTime() = this.apply {
    todayTime()
    this[Calendar.YEAR] = this[Calendar.YEAR] - 1
}

val dateFormat = SimpleDateFormat("dd/MM/yyyy")

fun Long.getDateFormatted(): String {
    val calendar = getCalendarInstanceFromUTC()
    calendar.timeInMillis = this
    return dateFormat.format(calendar.time)
}

fun Boolean?.andOrNull(bool: Boolean?) = if(this != null && bool != null) this && bool else null

fun Boolean?.notOrNull() = if(this != null ) !this else null
