package com.endava.internship.mobile.budgetplanner.ui.dashboard.income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.endava.internship.mobile.budgetplanner.databinding.FragmentTransactionsHolderBinding

class TransactionIncomesHolderFragment : Fragment() {

    lateinit var binding: FragmentTransactionsHolderBinding
    private val incomesViewModel by activityViewModels<IncomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionsHolderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        incomesViewModel.getData()
        val emptyTransactionFragment = TransactionIncomeEmptyFragment()
        val transactionExpenseFragment = TransactionsIncomeFragment()
        incomesViewModel.transactions.observe(viewLifecycleOwner) { transactions ->
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