package com.endava.internship.mobile.budgetplanner.ui.dashboard.expenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.endava.internship.mobile.budgetplanner.data.model.ExpenseTransactionsGeneralInfo
import com.endava.internship.mobile.budgetplanner.data.repository.TransactionCategoryRepository
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.providers.ResourceProvider
import com.endava.internship.mobile.budgetplanner.ui.base.BaseViewModel
import com.endava.internship.mobile.budgetplanner.ui.dashboard.transactions.TransactionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    val transactionCategoryRepository: TransactionCategoryRepository,
    val resourceProvider: ResourceProvider
) : BaseViewModel(resourceProvider) {

    private val _categories: MutableLiveData<List<TransactionModel>> = MutableLiveData()
    val categories: LiveData<List<TransactionModel>> = _categories

    private val _transactionsGeneralInfo: MutableLiveData<ExpenseTransactionsGeneralInfo> =
        MutableLiveData()
    val transactionsGeneralInfo: LiveData<ExpenseTransactionsGeneralInfo> = _transactionsGeneralInfo

    private suspend fun getCategories() {
        val response = transactionCategoryRepository.getExpenseCategories()
        when (response) {
            is Resource.Success -> _categories.value =
                response.value.map { TransactionModel(it.id, it.name, it.color, true) }
            is Resource.Failure -> pushStatusMessage(response.message)
        }
    }

    private suspend fun getTransactionsGeneralInfo() {
        val response = transactionCategoryRepository.getExpenseTransactionsGeneralInfo()
        when (response) {
            is Resource.Success -> {
                _transactionsGeneralInfo.value = response.value
                val transactions = response.value.expenseCategories
//                _categories.value = _categories.value?.filter { transactions.contains(it.name) }
//                    ?.forEach { transactionModel ->
//                        transactions[transactionModel.name]?.let { transactionModel.numberOfTransactions = it}
//                    }
            }
            is Resource.Failure -> pushStatusMessage(response.message)
        }
    }

    fun getData() = asyncExecute {
        getCategories()
        getTransactionsGeneralInfo()
    }

}
