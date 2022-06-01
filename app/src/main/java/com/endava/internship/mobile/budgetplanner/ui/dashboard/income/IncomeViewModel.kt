package com.endava.internship.mobile.budgetplanner.ui.dashboard.income

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
class IncomeViewModel @Inject constructor(
    val transactionCategoryRepository: TransactionCategoryRepository,
    val resourceProvider: ResourceProvider
) : BaseViewModel(resourceProvider) {

    private val _categories: MutableLiveData<List<TransactionModel>> = MutableLiveData()
    val categories: LiveData<List<TransactionModel>> = _categories

    fun getCategories() = asyncExecute {
        val response = transactionCategoryRepository.getIncomeCategories()
        when (response) {
            is Resource.Success -> _categories.value = response.value.map { TransactionModel(it.id, it.name, it.color) }
            is Resource.Failure -> pushStatusMessage(response.message)
        }
    }
}
