package com.example.data.di

import com.example.data.repository.ExerciseRepositoryImpl
import com.example.data.repository.UserAuthRepositoryImpl
import com.example.domain.repository.ExerciseRepository
import com.example.domain.repository.UserAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideExerciseRepository(
        exerciseRepositoryImpl: ExerciseRepositoryImpl
    ): ExerciseRepository

//    @Binds
//    @Singleton
//    abstract fun provideUserProfileRepository(userProfileRepositoryImpl: UserProfileRepositoryImpl): UserProfileRepository

    @Binds
    @Singleton
    abstract fun provideLoginRepository(userAuthRepositoryImpl: UserAuthRepositoryImpl): UserAuthRepository

//    @Binds
//    @Singleton
//    abstract fun provideNotificationRepository(notificationRepositoryImpl: NotificationRepositoryImpl): NotificationRepository
}