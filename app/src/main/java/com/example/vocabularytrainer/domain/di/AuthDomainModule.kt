package com.example.vocabularytrainer.domain.di

import com.example.vocabularytrainer.domain.auth.use_case.*
import com.example.vocabularytrainer.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AuthDomainModule {

    @ViewModelScoped
    @Provides
    fun provideTrackerUseCases(
        repository: AuthRepository
    ): AuthUseCases {
        return AuthUseCases(
            validateConfirmPassword = ValidateConfirmPassword(),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            registerUser = RegisterUser(repository)
        )
    }

}