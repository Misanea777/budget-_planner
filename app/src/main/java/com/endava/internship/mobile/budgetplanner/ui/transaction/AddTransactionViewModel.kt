package com.endava.internship.mobile.budgetplanner.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.endava.internship.mobile.budgetplanner.data.repository.TransactionCategoryRepository
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.providers.ResourceProvider
import com.endava.internship.mobile.budgetplanner.ui.base.BaseViewModel
import com.endava.internship.mobile.budgetplanner.ui.dashboard.transactions.TransactionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    val transactionCategoryRepository: TransactionCategoryRepository,
    val resourceProvider: ResourceProvider
) : BaseViewModel(resourceProvider) {

    private val _expensesCategories: MutableLiveData<List<TransactionModel>> = MutableLiveData()
    val expensesCategories: LiveData<List<TransactionModel>> = _expensesCategories

    private val _incomeCategories: MutableLiveData<List<TransactionModel>> = MutableLiveData()
    val incomeCategories: LiveData<List<TransactionModel>> = _incomeCategories

    fun getCategories() = asyncExecute {
        val expenseCategoriesResponse = transactionCategoryRepository.getExpenseCategories()
        val incomeCategoriesResponse = transactionCategoryRepository.getIncomeCategories()
        when (expenseCategoriesResponse) {
            is Resource.Success -> _expensesCategories.value =
                expenseCategoriesResponse.value.map { TransactionModel(it.id, it.name, it.color, true) }
            is Resource.Failure -> pushStatusMessage(expenseCategoriesResponse.message)
        }

        when (incomeCategoriesResponse) {
            is Resource.Success -> _incomeCategories.value =
                incomeCategoriesResponse.value.map { TransactionModel(it.id, it.name, it.color, false) }
            is Resource.Failure -> pushStatusMessage(incomeCategoriesResponse.message)
        }
    }
}