package com.endava.internship.mobile.budgetplanner.di

import com.endava.internship.mobile.budgetplanner.BuildConfig
import com.endava.internship.mobile.budgetplanner.data.local.preferences.UserPreferences
import com.endava.internship.mobile.budgetplanner.data.remote.*
import com.endava.internship.mobile.budgetplanner.data.repository.*
import com.endava.internship.mobile.budgetplanner.di.dispatchers.IoDispatcher
import com.endava.internship.mobile.budgetplanner.network.AuthorizationInterceptor
import com.endava.internship.mobile.budgetplanner.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .baseUrl(Constants.BASE_URL_RETROFIT_API)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor
    ) = OkHttpClient.Builder()
        .apply {
            if (BuildConfig.DEBUG) addInterceptor(httpLoggingInterceptor)
            addInterceptor(authorizationInterceptor)
        }.build()

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): IndustryApi =
        retrofit.create(IndustryApi::class.java)

    @Singleton
    @Provides
    fun provideAuthApiService(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideTransactionCategoryService(retrofit: Retrofit): TransactionCategoryApi =
        retrofit.create(TransactionCategoryApi::class.java)

    @Singleton
    @Provides
    fun provideBalanceService(retrofit: Retrofit): BalanceApi =
        retrofit.create(BalanceApi::class.java)

    @Singleton
    @Provides
    fun provideTransactionService(retrofit: Retrofit): TransactionApi =
        retrofit.create(TransactionApi::class.java)

    @Singleton
    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(
        userPreferences: UserPreferences,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): AuthorizationInterceptor = AuthorizationInterceptor(userPreferences, ioDispatcher)

    @Singleton
    @Provides
    fun provideDefaultIndustryRepository(
        api: IndustryApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): IndustryRepository = DefaultIndustryRepository(api, ioDispatcher)

    @Singleton
    @Provides
    fun provideDefaultAuthRepository(
        api: AuthApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        userPreferences: UserPreferences,
    ): AuthRepository =
        DefaultAuthRepository(api, ioDispatcher, userPreferences)

    @Singleton
    @Provides
    fun provideDefaultTransactionCategoryRepository(
        api: TransactionCategoryApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): TransactionCategoryRepository = DefaultTransactionCategoryRepository(api, ioDispatcher)

    @Singleton
    @Provides
    fun provideDefaultBalanceRepository(
        api: BalanceApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): BalanceRepository = DefaultBalanceRepository(api, ioDispatcher)

    @Singleton
    @Provides
    fun provideDefaultTransactionRepository(
        api: TransactionApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): TransactionRepository = DefaultTransactionRepository(api, ioDispatcher)
}
