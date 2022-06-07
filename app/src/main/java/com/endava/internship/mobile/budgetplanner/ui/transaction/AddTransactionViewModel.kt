package com.endava.internship.mobile.budgetplanner.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.data.model.ExpenseCategory
import com.endava.internship.mobile.budgetplanner.data.model.ExpenseTransaction
import com.endava.internship.mobile.budgetplanner.data.model.IncomeCategory
import com.endava.internship.mobile.budgetplanner.data.model.IncomeTransaction
import com.endava.internship.mobile.budgetplanner.data.repository.BalanceRepository
import com.endava.internship.mobile.budgetplanner.data.repository.TransactionCategoryRepository
import com.endava.internship.mobile.budgetplanner.data.repository.TransactionRepository
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.providers.ResourceProvider
import com.endava.internship.mobile.budgetplanner.ui.base.BaseViewModel
import com.endava.internship.mobile.budgetplanner.ui.dashboard.transactions.TransactionModel
import com.endava.internship.mobile.budgetplanner.util.*
import com.endava.internship.mobile.budgetplanner.util.validators.LiveDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    val balanceRepository: BalanceRepository,
    val transactionCategoryRepository: TransactionCategoryRepository,
    val transactionRepository: TransactionRepository,
    val resourceProvider: ResourceProvider
) : BaseViewModel(resourceProvider) {

    private val _expensesCategories: MutableLiveData<List<TransactionCategoryModel>> =
        MutableLiveData()
    val expensesCategories: LiveData<List<TransactionCategoryModel>> = _expensesCategories

    private val _incomeCategories: MutableLiveData<List<TransactionCategoryModel>> =
        MutableLiveData()
    val incomeCategories: LiveData<List<TransactionCategoryModel>> = _incomeCategories

    val isExpensesTransaction: MutableLiveData<Boolean> = MutableLiveData(true)

    var selectedCategoryName: String? = null
    var selectedCategoryNameError: MutableLiveData<String> = MutableLiveData()

    val selectedDate: MutableLiveData<Long> =
        MutableLiveData(getCalendarInstanceFromUTC().todayTime().timeInMillis)

    val title: MutableLiveData<String> = MutableLiveData()
    val titleValidator = LiveDataValidator(title).apply {
        addRule("The title can not be less than 5 characters long.") { value ->
            value?.length?.let { return@let it >= 5 } ?: false
        }
        addRule("The title can not be more than 25 characters long.") { value ->
            value?.length?.let { it <= 25 } ?: false
        }
        addRule("The title can't contain special characters.") { value ->
            value?.let { !it.hasMinimumOneSpecialChar() } ?: false
        }
    }

    val amount: MutableLiveData<String> = MutableLiveData<String>()
    val amountValidator = LiveDataValidator(amount).apply {
        addRule("The amount can not be less than 1.00.") { value ->
            value?.isGreaterOrEqualThan(1) ?: false
        }
        addRule("Woaah, too much money! Please spend something!") { value ->
            isExpensesTransaction.value?.let { if (it) return@addRule true }
            value?.isLessOrEqualThan(Constants.MAX_INITIAL_BALANCE) ?: false
        }
        addRule("The amount can not be greater than the currently available amount of ") { value ->
            isExpensesTransaction.value?.let { if (!it) return@addRule true }
            value?.isLessOrEqualThan(balance) ?: false
        }
    }

    val note: MutableLiveData<String> = MutableLiveData()

    var balance: Double? = null

    private fun validateForm(): Boolean {
        val isTitleValid = titleValidator.isValid()
        val isAmountValid = amountValidator.isValid()
        val isSelectedCategoryValid = selectedCategoryName != null
        selectedCategoryNameError.value =
            if (!isSelectedCategoryValid) "The transaction should have a category selected." else null

        return isTitleValid && isAmountValid && isSelectedCategoryValid
    }

    fun clearAllFields() {
        title.value = null
        titleValidator.error.value = null
        amount.value = null
        amountValidator.error.value = null
        note.value = null
        selectedDate.value = getCalendarInstanceFromUTC().todayTime().timeInMillis
        selectedCategoryName = null
        selectedCategoryNameError.value = null
    }


    init {
        getCurrentBalance()
        getCategories()
    }

    private fun getCategories() = asyncExecute {
        val expenseCategoriesResponse = transactionCategoryRepository.getExpenseCategories()
        val incomeCategoriesResponse = transactionCategoryRepository.getIncomeCategories()
        when (expenseCategoriesResponse) {
            is Resource.Success -> _expensesCategories.value =
                expenseCategoriesResponse.value.map {
                    TransactionCategoryModel(
                        it.id,
                        it.name,
                        it.color
                    )
                }
            is Resource.Failure -> pushStatusMessage(expenseCategoriesResponse.message)
        }

        when (incomeCategoriesResponse) {
            is Resource.Success -> _incomeCategories.value =
                incomeCategoriesResponse.value.map {
                    TransactionCategoryModel(
                        it.id,
                        it.name,
                        it.color
                    )
                }
            is Resource.Failure -> pushStatusMessage(incomeCategoriesResponse.message)
        }
    }

    private fun getCurrentBalance() = asyncExecute {
        val response = balanceRepository.getCurrentBalance()
        when (response) {
            is Resource.Success -> balance = response.value.amount
            is Resource.Failure -> pushStatusMessage(response.message)
        }
    }

    fun tryToAddTransaction() {
        val s = validateForm()
        if (s) addTransaction()
    }

    private val _receivedExpenseTransaction: MutableLiveData<ExpenseTransaction> = MutableLiveData()
    val receivedExpenseTransaction: LiveData<ExpenseTransaction> = _receivedExpenseTransaction

    private val _receivedIncomeTransaction: MutableLiveData<IncomeTransaction> = MutableLiveData()
    val receivedIncomeTransaction: LiveData<IncomeTransaction> = _receivedIncomeTransaction

    fun addTransaction() = asyncExecute {
        println("here")
        isExpensesTransaction.value?.let { isExpensesTransaction ->
            if (isExpensesTransaction) {
                val response = transactionRepository.addExpenseTransaction(
                    ExpenseTransaction(
                        amount.value?.toDouble(),
                        selectedDate.value?.getDateFormatted(),
                        ExpenseCategory(selectedCategoryName),
                        title.value,
                        note.value
                    )
                )
                when (response) {
                    is Resource.Success -> _receivedExpenseTransaction.value = response.value
                    is Resource.Failure -> pushStatusMessage(response.message)
                }
            } else {
                val response = transactionRepository.addIncomeTransaction(
                    IncomeTransaction(
                        amount.value?.toDouble(),
                        selectedDate.value?.getDateFormatted(),
                        IncomeCategory(selectedCategoryName),
                        title.value,
                        note.value
                    )
                )
                when (response) {
                    is Resource.Success -> _receivedIncomeTransaction.value = response.value
                    is Resource.Failure -> pushStatusMessage(response.message)
                }
            }
        }

    }
}
