package com.endava.internship.mobile.budgetplanner.ui.dashboard.expenses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.FragmentTransactionsEmptyBinding
import com.endava.internship.mobile.budgetplanner.ui.dashboard.DashboardFragmentDirections

class TransactionExpensesEmptyFragment : Fragment() {

    lateinit var binding: FragmentTransactionsEmptyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionsEmptyBinding.inflate(layoutInflater)
        binding.messageText.text = getString(R.string.dashboard_transaction_expense_empty_title)
        binding.addTransactionButton.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToAddTransactionFragment(
                    true
                )
            )
        }
        return binding.root
    }
}
