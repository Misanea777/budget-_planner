package com.endava.internship.mobile.budgetplanner.di

import android.content.Context
import com.endava.internship.mobile.budgetplanner.data.local.preferences.DefaultUserPreferences
import com.endava.internship.mobile.budgetplanner.data.local.preferences.UserPreferences
import com.endava.internship.mobile.budgetplanner.providers.ResourceProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Singleton
    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider =
        ResourceProvider(context)

    @Singleton
    @Provides
    fun provideDefaultUserPreferences(
        @ApplicationContext context: Context,
        gson: Gson
    ): UserPreferences = DefaultUserPreferences(context, gson)
}