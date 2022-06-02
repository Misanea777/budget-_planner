package com.endava.internship.mobile.budgetplanner.ui.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.FragmentAddTransactionBinding
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.ui.auth.login.LoginViewModel
import com.endava.internship.mobile.budgetplanner.ui.base.BaseFragment
import com.endava.internship.mobile.budgetplanner.ui.dashboard.transactions.TransactionModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTransactionFragment :
    BaseFragment<FragmentAddTransactionBinding>(FragmentAddTransactionBinding::inflate) {

    private val addTransactionViewModel by viewModels<AddTransactionViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = addTransactionViewModel
            lifecycleOwner = viewLifecycleOwner
        }

//        addTransactionViewModel.getCategories()
    }
}