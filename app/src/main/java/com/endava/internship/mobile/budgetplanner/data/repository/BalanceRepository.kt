package com.endava.internship.mobile.budgetplanner.data.repository

import com.endava.internship.mobile.budgetplanner.data.model.Balance
import com.endava.internship.mobile.budgetplanner.network.Resource

interface BalanceRepository {
    suspend fun getCurrentBalance(): Resource<Balance>
}
