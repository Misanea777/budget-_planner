package com.endava.internship.mobile.budgetplanner.ui.transaction.list

import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.FragmentTransactionsListBinding
import com.endava.internship.mobile.budgetplanner.ui.base.BaseFragment
import com.endava.internship.mobile.budgetplanner.ui.dialogs.ErrorDialog
import com.endava.internship.mobile.budgetplanner.ui.dialogs.YesNoDialog
import com.endava.internship.mobile.budgetplanner.util.asDollars
import com.endava.internship.mobile.budgetplanner.util.attachItemTouchHelper
import com.endava.internship.mobile.budgetplanner.util.toPrettyNumberFormat
import com.endava.internship.mobile.budgetplanner.util.toTwoDecimalPlaces
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionsListFragment :
    BaseFragment<FragmentTransactionsListBinding>(FragmentTransactionsListBinding::inflate) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TransactionsRecycleViewAdapter

    private val transactionsViewModel by viewModels<TransactionsViewModel>()

    private val args: TransactionsListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        transactionsViewModel.category.value = args.category
        transactionsViewModel.isExpensesTransaction.value = args.isExpenses
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        transactionsViewModel.isExpensesTransaction.observe(viewLifecycleOwner) { isExpensesTransaction ->
            transactionsViewModel.getTransactions(isExpensesTransaction, true)
        }

        transactionsViewModel.transactionsInfo.observe(viewLifecycleOwner) { transactionsCategory ->
            binding.titleText.text = transactionsCategory.category
            binding.monthAmountText.text = SpannableString(
                transactionsCategory.sumOfTransactions.toTwoDecimalPlaces().asDollars()
            ).toPrettyNumberFormat()
        }

        transactionsViewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            val transactionsAsTransactionModels = transactions.map {
                TransactionModel(
                    it.amount,
                    it.date,
                    it.id,
                    it.name,
                    it.note
                ) { position, id ->
                    getYesNoDialog {
                        transactionsViewModel.isExpensesTransaction.value?.let { isExpense ->
                            transactionsViewModel.deleteTransaction(it.id, isExpense)
                            delete(position, id)
                        }
                    }.show(childFragmentManager, "yes_no")
                }
            }
            viewAdapter.updateDataSet(transactionsAsTransactionModels.toMutableList())
        }

        transactionsViewModel.statusMessage.observe(viewLifecycleOwner) { statusMessage ->
            statusMessage.getContentIfNotHandled()?.let {
                showErrorDialog(this.getString(R.string.error_general), it)
            }
        }

        transactionsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            loadingDialogSetVisible(isLoading)
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(TransactionsListFragmentDirections.actionTransactionsListFragmentToDashboardFragment())
        }

        initRecycleView()
    }

    private fun delete(position: Int, id: Int) {
        transactionsViewModel.lastDeletedTransaction.observe(viewLifecycleOwner) { deletedID ->
            if (deletedID == id) {
                viewAdapter.removeItem(position)
                transactionsViewModel.isExpensesTransaction.value?.let {
                    transactionsViewModel.getTransactions(it)
                }
                getSuccessfulDeletionDialog().show(childFragmentManager, "transaction_deleted")
            }
        }
    }

    private fun initRecycleView() {
        viewAdapter = TransactionsRecycleViewAdapter()
        recyclerView = binding.recycleView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = viewAdapter
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.recycle_view_item_margin)))
            attachItemTouchHelper(TransactionItemTouchHelper(requireContext()).getItemTouchHelper())
        }
    }

    private fun getYesNoDialog(onYes: () -> Unit) = YesNoDialog(
        getString(R.string.yes_no_dialog_title),
        getString(R.string.yes_no_dialog_message),
        onYes
    )

    private fun getSuccessfulDeletionDialog() = ErrorDialog(
        getString(R.string.success_delete_dialog_title),
        getString(R.string.success_delete_dialog_message),
        getString(R.string.success_delete_dialog_button),
    )
}
