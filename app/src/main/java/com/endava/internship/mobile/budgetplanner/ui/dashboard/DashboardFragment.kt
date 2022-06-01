package com.endava.internship.mobile.budgetplanner.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.endava.internship.mobile.budgetplanner.databinding.FragmentDashboardBinding
import com.endava.internship.mobile.budgetplanner.ui.base.BaseFragment
import com.endava.internship.mobile.budgetplanner.ui.dashboard.expenses.CardExpensesFragment
import com.endava.internship.mobile.budgetplanner.ui.dashboard.expenses.TransactionsExpensesFragment
import com.endava.internship.mobile.budgetplanner.ui.dashboard.income.CardIncomeFragment
import com.endava.internship.mobile.budgetplanner.ui.dashboard.income.TransactionIncomeFragmentFragment
import com.endava.internship.mobile.budgetplanner.util.asDollars
import com.endava.internship.mobile.budgetplanner.util.toFancyNumberFormat
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {

    private val dashboardViewModel by viewModels<DashboardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val fragmentListCard = arrayListOf<Fragment>(
            CardExpensesFragment(),
            CardIncomeFragment(),
        )

        val adapterCard = ViewPagerAdapter(
            fragmentListCard,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val fragmentListTransactions = arrayListOf<Fragment>(
            TransactionsExpensesFragment(),
            TransactionIncomeFragmentFragment(),
        )

        val adapterTransactions = ViewPagerAdapter(
            fragmentListTransactions,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.cardViewPager.adapter = adapterCard
        binding.transactionsViewPager.adapter = adapterTransactions

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val text = binding.root.findViewById<TextView>(R.id.card_image_text)

        initTabLayout()

        dashboardViewModel.getCurrentBalance()

        dashboardViewModel.balance.observe(viewLifecycleOwner) { balance ->
            binding.balanceText.text = balance.toFancyNumberFormat().asDollars()
        }
    }

    private fun initTabLayout() {
        val titles = arrayOf("Expenses", "Income")
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, binding.cardViewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
        TabLayoutMediator(tabLayout, binding.transactionsViewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
}