package com.endava.internship.mobile.budgetplanner.di

import com.endava.internship.mobile.budgetplanner.BuildConfig
import com.endava.internship.mobile.budgetplanner.data.remote.AuthApi
import com.endava.internship.mobile.budgetplanner.data.remote.IndustryApi
import com.endava.internship.mobile.budgetplanner.data.repository.AuthRepository
import com.endava.internship.mobile.budgetplanner.data.repository.DefaultAuthRepository
import com.endava.internship.mobile.budgetplanner.data.repository.DefaultIndustryRepository
import com.endava.internship.mobile.budgetplanner.data.repository.IndustryRepository
import com.endava.internship.mobile.budgetplanner.di.dispatchers.IoDispatcher
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
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .apply { if (BuildConfig.DEBUG) addInterceptor(httpLoggingInterceptor) }
        .build()

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
    fun httpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideDefaultIndustryRepository(api: IndustryApi, @IoDispatcher ioDispatcher: CoroutineDispatcher): IndustryRepository = DefaultIndustryRepository(api, ioDispatcher);

    @Singleton
    @Provides
    fun provideDefaultAuthRepository(api: AuthApi, @IoDispatcher ioDispatcher: CoroutineDispatcher): AuthRepository = DefaultAuthRepository(api, ioDispatcher);
}
