package com.endava.internship.mobile.budgetplanner.ui.dashboard.income

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
class TransactionsIncomeFragment :
    BaseFragment<FragmentTransactionsBinding>(FragmentTransactionsBinding::inflate) {

    private val incomeViewModel by activityViewModels<IncomeViewModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TransactionRecycleViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycleView()
        incomeViewModel.getData()
        initObservers()
    }

    private fun initObservers() {
        incomeViewModel.transactions.observe(viewLifecycleOwner) { categories ->
            viewAdapter.updateDataSet(categories.toTypedArray())
        }

        incomeViewModel.statusMessage.observe(viewLifecycleOwner) { statusMessage ->
            statusMessage.getContentIfNotHandled()?.let {
                showErrorDialog(this.getString(R.string.error_general), it)
            }
        }

        incomeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            loadingDialogSetVisible(isLoading)
        }

        binding.addTransactionButton.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToAddTransactionFragment(
                    false
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
