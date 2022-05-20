package com.endava.internship.mobile.budgetplanner.data.repository

import com.endava.internship.mobile.budgetplanner.data.model.IndustryResponse
import com.endava.internship.mobile.budgetplanner.data.remote.IndustryApi
import com.endava.internship.mobile.budgetplanner.network.Resource
import com.endava.internship.mobile.budgetplanner.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher

class DefaultIndustryRepository (
    private val api: IndustryApi,
    private  val ioDispatcher: CoroutineDispatcher
    ) : IndustryRepository {

    override suspend fun getIndustries(): Resource<IndustryResponse> =
        safeApiCall(dispatcher = ioDispatcher) {
            api.getIndustries()
        }
}