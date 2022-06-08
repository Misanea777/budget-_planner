package com.endava.internship.mobile.budgetplanner.ui.dashboard.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.endava.internship.mobile.budgetplanner.databinding.FragmentTransactionsHolderBinding

class TransactionExpensesHolderFragment : Fragment() {

    lateinit var binding: FragmentTransactionsHolderBinding
    private val expensesViewModel by activityViewModels<ExpensesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionsHolderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        expensesViewModel.getData()
        val emptyTransactionFragment = TransactionExpensesEmptyFragment()
        val transactionExpenseFragment = TransactionsExpensesFragment()
        expensesViewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            if (transactions.isNotEmpty()) replaceFragment(transactionExpenseFragment) else replaceFragment(
                emptyTransactionFragment
            )
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerView.id, fragment)
            .commit()
    }
}
