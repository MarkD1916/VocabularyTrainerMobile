package com.example.vocabularytrainer.data.di

import com.androiddevs.ktornoteapp.data.remote.interceptors.TokenInterceptor
import com.example.vocabularytrainer.data.preferences.AuthPreference
import com.example.vocabularytrainer.data.repository.AuthRepositoryImpl
import com.example.vocabularytrainer.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun providesBaseUrl(): String = "http://10.0.2.2:8001"

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }
}