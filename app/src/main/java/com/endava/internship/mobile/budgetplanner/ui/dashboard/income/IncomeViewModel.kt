package com.endava.internship.mobile.budgetplanner.ui.dashboard.income

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.endava.internship.mobile.budgetplanner.data.model.IncomeTransactionsGeneralInfo
import com.endava.internship.mobile.budgetplanner.data.repository.TransactionCategoryRepository
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.providers.ResourceProvider
import com.endava.internship.mobile.budgetplanner.ui.base.BaseViewModel
import com.endava.internship.mobile.budgetplanner.ui.dashboard.transactions.TransactionCategoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    val transactionCategoryRepository: TransactionCategoryRepository,
    val resourceProvider: ResourceProvider
) : BaseViewModel(resourceProvider) {

    private var categories: List<TransactionCategoryModel>? = null

    private val _transactionsGeneralInfo: MutableLiveData<IncomeTransactionsGeneralInfo> =
        MutableLiveData()
    val transactionsGeneralInfo: LiveData<IncomeTransactionsGeneralInfo> = _transactionsGeneralInfo

    private val _transactions: MutableLiveData<List<TransactionCategoryModel>> = MutableLiveData()
    val transactions: LiveData<List<TransactionCategoryModel>> = _transactions

    private suspend fun getCategories() {
        val response = transactionCategoryRepository.getIncomeCategories()
        when (response) {
            is Resource.Success -> categories =
                response.value.map { TransactionCategoryModel(it.id, it.name, it.color, false) }
            is Resource.Failure -> pushStatusMessage(response.message)
        }
    }

    private suspend fun getTransactionsGeneralInfo() {
        val response = transactionCategoryRepository.getIncomeTransactionsGeneralInfo()
        when (response) {
            is Resource.Success -> {
                _transactionsGeneralInfo.value = response.value
                val transactions = response.value.incomeCategories
                _transactions.value = categories?.filter { transactions.contains(it.name) }
                    ?.onEach { transactionModel ->
                        transactions[transactionModel.name]?.let { transactionModel.numberOfTransactions = it}
                    }
            }
            is Resource.Failure -> pushStatusMessage(response.message)
        }
    }

    fun getData() = asyncExecute {
        getCategories()
        getTransactionsGeneralInfo()
    }
}
