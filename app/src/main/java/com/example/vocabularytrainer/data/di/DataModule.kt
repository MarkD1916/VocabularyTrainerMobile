package com.example.vocabularytrainer.data.di

import android.app.Application
import androidx.room.Room
import com.androiddevs.ktornoteapp.data.remote.interceptors.TokenInterceptor
import com.example.vocabularytrainer.data.preferences.AuthPreference
import com.example.vocabularytrainer.data.remote.auth.api.AuthApi
import com.example.vocabularytrainer.data.local.home.VocabularyDatabase
import com.example.vocabularytrainer.data.local.home.dao.VocabularyDao
import com.example.vocabularytrainer.data.remote.home.remote.api.HomeApi
import com.example.vocabularytrainer.data.repository.AuthRepositoryImpl
import com.example.vocabularytrainer.data.repository.HomeRepositoryImpl
import com.example.vocabularytrainer.domain.repository.AuthRepository
import com.example.vocabularytrainer.domain.repository.HomeRepository
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
object DataModule {

    @Singleton
    @Provides
    fun provideTokenInterceptor(authSharedPreferences: AuthPreference): TokenInterceptor =
        TokenInterceptor(authSharedPreferences)

    @Singleton
    @Provides
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()
    }


    @Provides
    @Named("VocabularyUrl")
    fun providesBaseUrlAuth(): String = "https://vmakd1916vocabularytrainer.herokuapp.com"


    @Provides
    @Singleton
    @Named("Vocabulary")
    fun provideVocabularyRetrofit(@Named("VocabularyUrl")BASE_URL: String, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build()


    @Provides
    @Singleton
    fun provideAuthApi(@Named("Vocabulary")retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeApi(@Named("Vocabulary")retrofit: Retrofit): HomeApi {
        return retrofit.create(HomeApi::class.java)
    }


    @Provides
    @Singleton
    fun provideTrackerDatabase(app: Application): VocabularyDatabase {
        return Room.databaseBuilder(
            app,
            VocabularyDatabase::class.java,
            "vocabulary_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideVocabularyDao(db: VocabularyDatabase) = db.dao

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authApi)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(homeApi: HomeApi, dao: VocabularyDao): HomeRepository {
        return HomeRepositoryImpl(homeApi,dao)
    }

}