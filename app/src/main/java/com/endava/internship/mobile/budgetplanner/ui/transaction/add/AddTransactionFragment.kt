package com.endava.internship.mobile.budgetplanner.ui.transaction.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.android.material.datepicker.MaterialDatePicker
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

        addTransactionViewModel.balance.observe(viewLifecycleOwner) {
            addTransactionViewModel.updateExpensesRuleErrorMsg()
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
            createSuccessDialog(
                "${getString(R.string.add_transaction_expense_success_message_prefix)} ${receivedExpenseTransaction.amount} ${
                    getString(R.string.add_transaction_success_message_suffix)}")
                .show(childFragmentManager, "success_expense_transaction")
        }

        addTransactionViewModel.receivedIncomeTransaction.observe(viewLifecycleOwner) { receivedIncomeTransaction ->
            createSuccessDialog(
                "${getString(R.string.add_transaction_income_success_message_prefix)} ${receivedIncomeTransaction.amount} ${
                    getString(R.string.add_transaction_success_message_suffix)}")
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
                addTransactionViewModel.selectedCategoryName.value = selected?.text.toString()
            }
        }

        binding.editTextAmount.filters = arrayOf(DecimalDigitsInputFilter(18, 2))

        initDatePicker()

    }

    private fun initDatePicker() {

        val calendar = getCalendarInstanceFromUTC()
        val datePickerDialog = DatePickerDialog(
            childFragmentManager,
            getString(R.string.add_transaction_date_picker_title),
            calendar.oneYearAgoTime().timeInMillis,
            calendar.todayTime().timeInMillis,
            MaterialDatePicker.todayInUtcMilliseconds()
        ) { selected ->
            selected?.let {
                addTransactionViewModel.selectedDate.value = selected.getDateFormatted()
            }
        }

        binding.calendarText.setOnClickListener {
            datePickerDialog.apply {
                addTransactionViewModel.selectedDate.value?.getLongFromFormattedDate()
                    ?.let { startSelection = it }
                show()
            }
        }
    }

    private fun goToPreviousScreen() {
        findNavController().navigate(AddTransactionFragmentDirections.actionAddTransactionFragmentToDashboardFragment())
    }

    private fun createSuccessDialog(msg: String) = SuccessTransactionDialog(
        getString(R.string.add_transaction_success_dialog_title),
        msg,
        onDone = { goToPreviousScreen() },
        onAddAnother = {
            addTransactionViewModel.clearAllFields()
            binding.chipGroup.clearCheck()
        }
    )
}
