package com.example.vocabularytrainer.domain.di

import com.example.vocabularytrainer.domain.auth.use_case.*
import com.example.vocabularytrainer.domain.home.use_case.DeleteGroup
import com.example.vocabularytrainer.domain.home.use_case.GetAllGroup
import com.example.vocabularytrainer.domain.home.use_case.HomeUseCases
import com.example.vocabularytrainer.domain.repository.AuthRepository
import com.example.vocabularytrainer.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @ViewModelScoped
    @Provides
    fun provideAuthUseCases(
        repository: AuthRepository
    ): AuthUseCases {
        return AuthUseCases(
            validateConfirmPassword = ValidateConfirmPassword(),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            registerUser = RegisterUser(repository),
            loginUser = LoginUser(repository),
            logoutUser = LogoutUser(repository)
        )
    }

    @ViewModelScoped
    @Provides
    fun provideHomeUseCases(
        repository: HomeRepository
    ): HomeUseCases {
        return HomeUseCases(
            getAllGroup = GetAllGroup(repository),
            deleteGroup = DeleteGroup(repository)
        )
    }

}