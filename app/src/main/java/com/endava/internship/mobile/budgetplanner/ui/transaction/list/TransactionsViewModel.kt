package com.endava.internship.mobile.budgetplanner.ui.transaction.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.endava.internship.mobile.budgetplanner.data.model.CompleteTransaction
import com.endava.internship.mobile.budgetplanner.data.model.TransactionsCategory
import com.endava.internship.mobile.budgetplanner.data.repository.TransactionRepository
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.providers.ResourceProvider
import com.endava.internship.mobile.budgetplanner.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    val transactionRepository: TransactionRepository,
    val resourceProvider: ResourceProvider
) : BaseViewModel(resourceProvider) {

    val category: MutableLiveData<String> = MutableLiveData()
    val isExpensesTransaction: MutableLiveData<Boolean> = MutableLiveData()

    private val _lastDeletedTransaction: MutableLiveData<Int> = MutableLiveData()
    val lastDeletedTransaction: LiveData<Int> = _lastDeletedTransaction

    private val _transactionsInfo: MutableLiveData<TransactionsCategory> = MutableLiveData()
    val transactionsInfo: LiveData<TransactionsCategory> = _transactionsInfo

    private val _transactions: MutableLiveData<List<CompleteTransaction>> = MutableLiveData()
    val transactions: LiveData<List<CompleteTransaction>> = _transactions


    fun getTransactions(isExpenses: Boolean, updateCurrentTransactions: Boolean = false) = asyncExecute {
        val response = category.value?.let { transactionRepository.getTransactionsCategory(it, isExpenses) }
        when (response) {
            is Resource.Success -> {
                _transactionsInfo.value = response.value
                if(updateCurrentTransactions) _transactions.value = response.value.transactions
            }
            is Resource.Failure -> pushStatusMessage(response.message)
        }
    }

    fun deleteTransaction(id: Int, isExpenses: Boolean) = asyncExecute {
        val response = transactionRepository.deleteTransaction(id, isExpenses)
        when(response) {
            is Resource.Success -> _lastDeletedTransaction.value = id
            is Resource.Failure -> pushStatusMessage(response.message)
        }
    }
}