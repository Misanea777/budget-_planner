package com.endava.internship.mobile.budgetplanner.ui.dialogs

import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.*

class DatePickerDialog(
    private val fragmentManager: FragmentManager,
    val title: String,
    private val startDateConstraint: Long? = null,
    private val endDateConstraint: Long? = null,
    private val startSelection: Long = MaterialDatePicker.todayInUtcMilliseconds()
) {
    val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(title)
            .setSelection(startSelection)
            .setCalendarConstraints(buildConstrains())
            .build()

    fun show() {
        if (!datePicker.isAdded) datePicker.show(fragmentManager, "date_picker")
    }

    private fun buildConstrains(): CalendarConstraints {
        val listValidators = arrayListOf<CalendarConstraints.DateValidator>()
        val constraintsBuilder = CalendarConstraints.Builder()
        if (startDateConstraint != null) {
            listValidators.add(DateValidatorPointForward.from(startDateConstraint))
            constraintsBuilder.setStart(startDateConstraint)
        }
        if (endDateConstraint != null) {
            listValidators.add(DateValidatorPointBackward.before(endDateConstraint))
            constraintsBuilder.setEnd(endDateConstraint)
        }

        return constraintsBuilder.setValidator(CompositeDateValidator.allOf(listValidators)).build()
    }
}
