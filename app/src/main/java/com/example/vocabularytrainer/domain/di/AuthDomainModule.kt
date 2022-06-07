package com.example.vocabularytrainer.domain.di

import com.example.vocabularytrainer.domain.auth.use_case.AuthUseCases
import com.example.vocabularytrainer.domain.auth.use_case.ValidateConfirmPassword
import com.example.vocabularytrainer.domain.auth.use_case.ValidateEmail
import com.example.vocabularytrainer.domain.auth.use_case.ValidatePassword
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
    ): AuthUseCases {
        return AuthUseCases(
            validateConfirmPassword = ValidateConfirmPassword(),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword()
        )
    }

}