package com.endava.internship.mobile.budgetplanner.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.endava.internship.mobile.budgetplanner.R
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
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {

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

        binding.apply {
            viewModel = dashboardViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        initObservers()

        initTabLayout()

        dashboardViewModel.getCurrentBalance()

        dashboardViewModel.balance.observe(viewLifecycleOwner) { balance ->
            binding.balanceText.text = balance.toFancyNumberFormat().asDollars()
        }
    }

    private fun initTabLayout() {
        val titles = arrayOf(
            this.getString(R.string.general_expenses_title),
            this.getString(R.string.general_income_title)
        )
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, binding.cardViewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
        TabLayoutMediator(tabLayout, binding.transactionsViewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    private fun initObservers() {
        dashboardViewModel.isLoggedOut.observe(viewLifecycleOwner) { isLoggedOut ->
            if (isLoggedOut) findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToLoginFragment())
        }

        dashboardViewModel.statusMessage.observe(viewLifecycleOwner) { statusMessage ->
            statusMessage.getContentIfNotHandled()?.let {
                showErrorDialog(this.getString(R.string.error_general), it)
            }
        }

        dashboardViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            loadingDialogSetVisible(isLoading)
        }
    }
}
