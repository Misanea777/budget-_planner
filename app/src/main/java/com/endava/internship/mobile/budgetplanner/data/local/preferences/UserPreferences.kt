package com.endava.internship.mobile.budgetplanner.data.local.preferences

import com.endava.internship.mobile.budgetplanner.data.model.LoggedUser
import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    val loggedUser: Flow<LoggedUser?>
    suspend fun saveLoggedUser(loggedUser: LoggedUser)
}
