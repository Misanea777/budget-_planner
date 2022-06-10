package com.endava.internship.mobile.budgetplanner.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.endava.internship.mobile.budgetplanner.data.model.LoggedUser
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data_store")

class DefaultUserPreferences(val context: Context, private val gson: Gson) : UserPreferences {
    private val applicationContext = context.applicationContext

    override val loggedUser: Flow<LoggedUser?>
        get() = applicationContext.dataStore.data.map { userDataStore ->
            gson.fromJson(userDataStore[LOGGED_USER], LoggedUser::class.java)
        }

    override suspend fun saveLoggedUser(loggedUser: LoggedUser) {
        applicationContext.dataStore.edit { userDataStore ->
            userDataStore[LOGGED_USER] = gson.toJson(loggedUser)
        }
    }

    companion object{
        private val LOGGED_USER = stringPreferencesKey("logged_user")
    }

}
