package com.endava.internship.mobile.budgetplanner.data.repository

import com.endava.internship.mobile.budgetplanner.data.model.IndustryResponse
import com.endava.internship.mobile.budgetplanner.network.Resource

interface IndustryRepository {
    suspend fun getIndustries(): Resource<IndustryResponse>
}
