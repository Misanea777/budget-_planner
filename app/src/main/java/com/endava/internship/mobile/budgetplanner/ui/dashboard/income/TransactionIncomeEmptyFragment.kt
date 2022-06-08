package com.endava.internship.mobile.budgetplanner.ui.dashboard.income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.endava.internship.mobile.budgetplanner.databinding.FragmentTransactionsEmptyBinding
import com.endava.internship.mobile.budgetplanner.ui.dashboard.DashboardFragmentDirections

class TransactionIncomeEmptyFragment : Fragment() {

    lateinit var binding: FragmentTransactionsEmptyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionsEmptyBinding.inflate(layoutInflater)
        binding.messageText.text = "You haven’t added any income\nfor this month yet."
        binding.addTransactionButton.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToAddTransactionFragment(
                    false
                )
            )
        }
        return binding.root
    }
}
