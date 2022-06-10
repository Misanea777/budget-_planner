package com.endava.internship.mobile.budgetplanner.data.repository

import com.endava.internship.mobile.budgetplanner.data.model.Balance
import com.endava.internship.mobile.budgetplanner.data.remote.BalanceApi
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher

class DefaultBalanceRepository (
    private val api: BalanceApi,
    private val ioDispatcher: CoroutineDispatcher
) : BalanceRepository {

    override suspend fun getCurrentBalance(): Resource<Balance> = safeApiCall(dispatcher = ioDispatcher) {
        api.getCurrentBalance()
    }
}
