package com.endava.internship.mobile.budgetplanner.data.remote

import com.endava.internship.mobile.budgetplanner.data.model.IndustryResponse
import com.endava.internship.mobile.budgetplanner.util.validators.Constants
import retrofit2.http.GET

interface IndustryApi {

    @GET(Constants.ApiPaths.INDUSTRY_API_PATH)
    suspend fun getIndustries(): IndustryResponse
}