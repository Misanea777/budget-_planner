package com.endava.internship.mobile.budgetplanner.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.FragmentAddTransactionBinding
import com.endava.internship.mobile.budgetplanner.ui.base.BaseFragment
import com.endava.internship.mobile.budgetplanner.ui.dialogs.DatePickerDialog
import com.endava.internship.mobile.budgetplanner.ui.dialogs.SuccessTransactionDialog
import com.endava.internship.mobile.budgetplanner.util.*
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddTransactionFragment :
    BaseFragment<FragmentAddTransactionBinding>(FragmentAddTransactionBinding::inflate) {

    private val addTransactionViewModel by viewModels<AddTransactionViewModel>()

    private lateinit var adapter: TransactionChipGroupViewAdapter

    private val args: AddTransactionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        addTransactionViewModel.isExpensesTransaction.value = args.isFromExpenses
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = addTransactionViewModel
            lifecycleOwner = viewLifecycleOwner
        }


        addTransactionViewModel.statusMessage.observe(viewLifecycleOwner) { statusMessage ->
            statusMessage.getContentIfNotHandled()?.let {
                showErrorDialog(this.getString(R.string.error_general), it)
            }
        }

        addTransactionViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            loadingDialogSetVisible(isLoading)
        }

        addTransactionViewModel.receivedExpenseTransaction.observe(viewLifecycleOwner) { receivedExpenseTransaction ->
            createSuccessDialog("A new Expense of ${receivedExpenseTransaction.amount} has been added.")
                .show(childFragmentManager, "success_expense_transaction")
        }

        addTransactionViewModel.receivedIncomeTransaction.observe(viewLifecycleOwner) { receivedIncomeTransaction ->
            createSuccessDialog("A new Income of ${receivedIncomeTransaction.amount} has been added.")
                .show(childFragmentManager, "success_expense_transaction")
        }

        binding.backButton.setOnClickListener {
            goToPreviousScreen()
        }

        adapter = TransactionChipGroupViewAdapter(layoutInflater, binding.chipGroup)

        addTransactionViewModel.isExpensesTransaction.observe(viewLifecycleOwner) { isExpensesTransaction ->
            addTransactionViewModel.clearAllFields()
            if (isExpensesTransaction) {
                addTransactionViewModel.expensesCategories.observe(viewLifecycleOwner) { expensesCategories ->
                    adapter.updateDataSet(expensesCategories)
                }
            } else {
                addTransactionViewModel.incomeCategories.observe(viewLifecycleOwner) { incomeCategories ->
                    adapter.updateDataSet(incomeCategories)
                }
            }

        }

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            group.setUncheckedChipsAlpha(checkedIds, 0.2f)
            var selected: Chip? = null
            checkedIds.firstOrNull()?.let { selected = group.findViewById(it) }
            selected?.let {
                addTransactionViewModel.selectedCategoryName = selected?.text.toString()
            }
        }

        binding.editTextAmount.filters = arrayOf(DecimalDigitsInputFilter(18, 2))

        initDatePicker()

    }

    private fun initDatePicker() {

        val calendar = getCalendarInstanceFromUTC()
        val datePickerDialog = DatePickerDialog(
            childFragmentManager,
            "Pick a transaction date",
            calendar.oneYearAgoTime().timeInMillis,
            calendar.todayTime().timeInMillis
        )

        binding.calendarText.setOnClickListener {
            datePickerDialog.show()
        }

        datePickerDialog.datePicker.addOnPositiveButtonClickListener {
            val selected = datePickerDialog.datePicker.selection
            selected?.let { addTransactionViewModel.selectedDate.value = selected }
        }

        addTransactionViewModel.selectedDate.observe(viewLifecycleOwner) { selectedDate ->
            binding.calendarText.text = selectedDate.getDateFormatted()
        }
    }

    private fun goToPreviousScreen() {
        findNavController().navigate(AddTransactionFragmentDirections.actionAddTransactionFragmentToDashboardFragment())
    }

    private fun createSuccessDialog(msg: String) = SuccessTransactionDialog(
        "Success",
        msg,
        onDone = { goToPreviousScreen() },
        onAddAnother = {
            addTransactionViewModel.clearAllFields()
            binding.chipGroup.clearCheck()
        }
    )
}
