package com.endava.internship.mobile.budgetplanner.ui.dashboard.expenses

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.FragmentTransactionsBinding
import com.endava.internship.mobile.budgetplanner.ui.base.BaseFragment
import com.endava.internship.mobile.budgetplanner.ui.dashboard.DashboardFragmentDirections
import com.endava.internship.mobile.budgetplanner.ui.dashboard.transactions.TransactionRecycleViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionsExpensesFragment :
    BaseFragment<FragmentTransactionsBinding>(FragmentTransactionsBinding::inflate) {

    private val expensesViewModel by activityViewModels<ExpensesViewModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TransactionRecycleViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycleView()
        expensesViewModel.getData()
        initObservers()
    }

    private fun initObservers() {
        expensesViewModel.categories.observe(viewLifecycleOwner) { categories ->
            viewAdapter.updateDataSet(categories.toTypedArray())
        }

        expensesViewModel.statusMessage.observe(viewLifecycleOwner) { statusMessage ->
            statusMessage.getContentIfNotHandled()?.let {
                showErrorDialog(this.getString(R.string.error_general), it)
            }
        }

        expensesViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            loadingDialogSetVisible(isLoading)
        }

        binding.addTransactionButton.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToAddTransactionFragment(
                    true
                )
            )
        }
    }

    private fun initRecycleView() {
        viewAdapter = TransactionRecycleViewAdapter(emptyArray())
        recyclerView = binding.recycleView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = viewAdapter
        }
    }
}
