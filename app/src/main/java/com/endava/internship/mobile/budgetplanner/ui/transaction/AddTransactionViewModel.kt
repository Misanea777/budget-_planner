package com.endava.internship.mobile.budgetplanner.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import com.endava.internship.mobile.budgetplanner.util.*
import com.endava.internship.mobile.budgetplanner.util.validators.LiveDataStepValidatorResolver
import com.endava.internship.mobile.budgetplanner.util.validators.LiveDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val selectedCategoryName: MutableLiveData<String> = MutableLiveData()
    val selectedCategoryNameValidator = LiveDataValidator(selectedCategoryName).apply {
        addRule("The transaction should have a category selected.") {
            it != null
        }
    }

    val selectedDate: MutableLiveData<String> = MutableLiveData()
    val selectedDateValidator = LiveDataValidator(selectedDate).apply {
        addRule("The date can not be empty") {
            it != null
        }
    }

    val title: MutableLiveData<String> = MutableLiveData()
    val titleValidator = LiveDataValidator(title).apply {
        addRule("The title can not be less than 5 characters long.") { value ->
            value?.length?.let { return@let it >= 5 } ?: false
        }
        addRule("The title can not be more than 25 characters long.") { value ->
            value?.length?.let { it <= 25 } ?: false
        }
        addRule("The title should contain only alpha characters.") { value ->
            value?.let { it.containsOnlyAlphaChar() } ?: false
        }
    }

    val note: MutableLiveData<String> = MutableLiveData()

    val balance: MutableLiveData<Double> = MutableLiveData()

    private val expensesRuleErrorMsg: MutableLiveData<String> = MutableLiveData()

    val amount: MutableLiveData<String> = MutableLiveData<String>()
    val amountValidator = LiveDataValidator(amount).apply {
        addRule("The amount can not be less than 1.00.") { value ->
            value?.isGreaterOrEqualThan(1) ?: false
        }
        addRule("Woaah, too much money! Please spend something!") { value ->
            isExpensesTransaction.value?.let { if (it) return@addRule true }
            value?.isLessOrEqualThan(Constants.MAX_INITIAL_BALANCE) ?: false
        }
        addRule(expensesRuleErrorMsg) { value ->
            isExpensesTransaction.value?.let { if (!it) return@addRule true }
            value?.isLessOrEqualThan(balance.value) ?: false
        }
    }

    fun updateExpensesRuleErrorMsg() {
        expensesRuleErrorMsg.value = "The amount can not be greater than the currently available amount of " + balance.value?.toTwoDecimalPlaces()
    }

    private fun validateForm(): Boolean {
        clearAllErrors()
        return LiveDataStepValidatorResolver(listOf(
            titleValidator,
            amountValidator,
            selectedDateValidator,
            selectedCategoryNameValidator
        )).isValid()
    }

    fun clearAllFields() {
        title.value = null
        amount.value = null
        note.value = null
        selectedDate.value = null
        selectedCategoryName.value = null
        clearAllErrors()
    }

    private fun clearAllErrors() {
        titleValidator.error.value = null
        amountValidator.error.value = null
        selectedDateValidator.error.value = null
        selectedCategoryNameValidator.error.value = null
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
                }.sortedByDescending { it.id }
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
                }.sortedByDescending { it.id }
            is Resource.Failure -> pushStatusMessage(incomeCategoriesResponse.message)
        }
    }

    private fun getCurrentBalance() = asyncExecute {
        println("in balan")
        val response = balanceRepository.getCurrentBalance()
        when (response) {
            is Resource.Success -> balance.value = response.value.amount
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
        isExpensesTransaction.value?.let { isExpensesTransaction ->
            if (isExpensesTransaction) {
                val response = transactionRepository.addExpenseTransaction(
                    ExpenseTransaction(
                        amount.value?.toDouble(),
                        selectedDate.value,
                        ExpenseCategory(selectedCategoryName.value),
                        title.value,
                        note.value
                    )
                )
                when (response) {
                    is Resource.Success -> {
                        response.value.amount?.let { balance.value = balance.value?.minus(it) }
                        _receivedExpenseTransaction.value = response.value
                    }
                    is Resource.Failure -> pushStatusMessage(response.message)
                }
            } else {
                val response = transactionRepository.addIncomeTransaction(
                    IncomeTransaction(
                        amount.value?.toDouble(),
                        selectedDate.value,
                        IncomeCategory(selectedCategoryName.value),
                        title.value,
                        note.value
                    )
                )
                when (response) {
                    is Resource.Success -> {
                        response.value.amount?.let { balance.value = balance.value?.plus(it) }
                        _receivedIncomeTransaction.value = response.value
                    }
                    is Resource.Failure -> pushStatusMessage(response.message)
                }
            }
        }
    }
}
