package com.example.vocabularytrainer.domain.di

import com.example.vocabularytrainer.data.preferences.AuthPreference
import com.example.vocabularytrainer.domain.auth.use_case.*
import com.example.vocabularytrainer.domain.home.use_case.AddGroup
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
            logoutUser = LogoutUser(repository),
            getCurrentUser = GetCurrentUser(repository)
        )
    }

    @ViewModelScoped
    @Provides
    fun provideHomeUseCases(
        repository: HomeRepository,
        authPreference: AuthPreference
    ): HomeUseCases {
        return HomeUseCases(
            getAllGroup = GetAllGroup(repository),
            deleteGroup = DeleteGroup(repository),
            addGroup = AddGroup(repository,authPreference)
        )
    }

}