package com.example.vocabularytrainer.data.di

import com.androiddevs.ktornoteapp.data.remote.interceptors.TokenInterceptor
import com.example.vocabularytrainer.data.preferences.AuthPreference
import com.example.vocabularytrainer.data.remote.auth.api.AuthApi
import com.example.vocabularytrainer.data.remote.auth.api.CountryApi
import com.example.vocabularytrainer.data.repository.AuthRepositoryImpl
import com.example.vocabularytrainer.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDataModule {

    @Singleton
    @Provides
    fun provideTokenAuthInterceptor(authSharedPreferences: AuthPreference): TokenInterceptor =
        TokenInterceptor(authSharedPreferences)

    @Singleton
    @Provides
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()
    }


    @Provides
    @Named("AuthUrl")
    fun providesBaseUrlAuth(): String = "https://vmakd1916vocabularytrainer.herokuapp.com"

    @Provides
    @Named("CountryUrl")
    fun providesBaseUrlCountry(): String = "https://restcountries.com/v3.1"

    @Provides
    @Singleton
    @Named("Auth")
    fun provideAuthRetrofit(@Named("AuthUrl")BASE_URL: String, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build()




    @Provides
    @Singleton
    fun provideAuthApi(@Named("Auth")retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    @Named("Country")
    fun provideCountryRetrofit(@Named("CountryUrl")BASE_URL: String, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideCountryApi(@Named("Country")retrofit: Retrofit): CountryApi {
        return retrofit.create(CountryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authApi)
    }
}