package com.endava.internship.mobile.budgetplanner.ui.dialogs

import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.*

class DatePickerDialog(
    private val fragmentManager: FragmentManager,
    var title: String,
    var startDateConstraint: Long,
    var endDateConstraint: Long,
    var startSelection: Long,
    val onPositiveButtonClickListener: (selected: Long?) -> Unit
) {
    var datePicker = initDatePicker()

    private fun initDatePicker():  MaterialDatePicker<Long> = MaterialDatePicker.Builder.datePicker()
        .setTitleText(title)
        .setSelection(startSelection)
        .setCalendarConstraints(buildConstrains())
        .build().apply {
            addOnPositiveButtonClickListener {
                selection?.let { startSelection = it }
                onPositiveButtonClickListener.invoke(selection)
            }
        }

    fun show() {
        datePicker = initDatePicker()
        if (!datePicker.isAdded) datePicker.show(fragmentManager, "date_picker")
    }

    private fun buildConstrains(): CalendarConstraints {
        val listValidators = arrayListOf<CalendarConstraints.DateValidator>()
        val constraintsBuilder = CalendarConstraints.Builder()

        listValidators.add(DateValidatorPointForward.from(startDateConstraint))
        constraintsBuilder.setStart(startDateConstraint)

        listValidators.add(DateValidatorPointBackward.before(endDateConstraint))
        constraintsBuilder.setEnd(endDateConstraint)

        constraintsBuilder.setOpenAt(startSelection)

        return constraintsBuilder.setValidator(CompositeDateValidator.allOf(listValidators)).build()
    }
}
