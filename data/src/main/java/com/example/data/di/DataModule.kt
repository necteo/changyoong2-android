package com.example.data.di

import android.content.Context
import com.example.data.datasource.local.UserAuthLocalDataSource
import com.example.data.datasource.local.UserAuthLocalDataSourceImpl
import com.example.data.datasource.local.preferences.PreferenceManager
import com.example.data.datasource.remote.ExerciseRemoteDataSource
import com.example.data.datasource.remote.ExerciseRemoteDataSourceImpl
import com.example.data.datasource.remote.UserAuthRemoteDataSource
import com.example.data.datasource.remote.UserAuthRemoteDataSourceImpl
import com.example.data.datasource.remote.service.ExerciseService
import com.example.data.datasource.remote.service.GoogleLoginService
import com.example.data.datasource.remote.service.UserAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    internal fun provideExerciseRemoteDataSource(
        exerciseService: ExerciseService,
        preferences: PreferenceManager
    ): ExerciseRemoteDataSource {
        return ExerciseRemoteDataSourceImpl(exerciseService, preferences)
    }

    @Singleton
    @Provides
    internal fun provideUserAuthRemoteDataSource(
        googleLoginService: GoogleLoginService,
        userAuthService: UserAuthService,
        preferences: PreferenceManager
    ): UserAuthRemoteDataSource {
        return UserAuthRemoteDataSourceImpl(userAuthService, googleLoginService, preferences)
    }

    @Singleton
    @Provides
    internal fun provideUserAuthLocalDataSource(preferenceManager: PreferenceManager): UserAuthLocalDataSource {
        return UserAuthLocalDataSourceImpl(preferenceManager)
    }

    @Singleton
    @Provides
    internal fun providePreferenceManager(@ApplicationContext context: Context) = PreferenceManager(context)
}