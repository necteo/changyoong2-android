package com.example.data.di

import com.example.data.datasource.local.preferences.PreferenceManager
import com.example.data.datasource.remote.AccessTokenInterceptor
import com.example.data.datasource.remote.service.ExerciseService
import com.example.data.datasource.remote.service.GoogleLoginService
import com.example.data.datasource.remote.service.UserAuthService
import com.example.data.util.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@OptIn(ExperimentalSerializationApi::class)
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val GOOGLE_BASE_URL = "https://oauth2.googleapis.com"
    private const val OUNMO_BASE_URL = "http://10.0.2.2:8080/ounmo/"

//    @Provides
//    internal fun provideAccessTokenAuthenticator(
//        preferences: PreferenceManager
//    ): AccessTokenAuthenticator {
//        return AccessTokenAuthenticator(preferences)
//    }

    @Provides
    internal fun provideAuthenticationInterceptor(
        preferences: PreferenceManager
    ): AccessTokenInterceptor {
        return AccessTokenInterceptor(preferences)
    }

    @Provides
    @Named("OunmoClient")
    internal fun provideOkHttpClient(
//        accessTokenAuthenticator: AccessTokenAuthenticator,
        accessTokenInterceptor: AccessTokenInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
//            .authenticator(accessTokenAuthenticator) // To update the token when it gets HTTP unauthorized error
            .addInterceptor(accessTokenInterceptor) // To set the token in the header
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Named("LoginClient")
    internal fun provideOkHttpClientForLogin(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    @Named("OunmoApi")
    internal fun provideRetrofit(
        @Named("OunmoClient")
        okHttpClient: OkHttpClient,
    ): Retrofit {
        val format = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(format.asConverterFactory(contentType))
            .client(okHttpClient)
            .baseUrl(OUNMO_BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    @Named("GoogleLogin")
    internal fun provideRetrofitForGoogleLogin(
        @Named("LoginClient")
        okHttpClient: OkHttpClient,
    ): Retrofit {
        val format = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .addConverterFactory(format.asConverterFactory(contentType))
            .client(okHttpClient)
            .baseUrl(GOOGLE_BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    @Named("OunmoLogin")
    internal fun provideRetrofitForUserAuth(
        @Named("OunmoClient")
        okHttpClient: OkHttpClient,
    ): Retrofit {
        val format = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(format.asConverterFactory(contentType))
            .client(okHttpClient)
            .baseUrl(OUNMO_BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    internal fun provideGoogleLoginService(@Named("GoogleLogin") retrofit: Retrofit): GoogleLoginService {
        return retrofit.create(GoogleLoginService::class.java)
    }

    @Singleton
    @Provides
    internal fun provideUserAuthService(@Named("OunmoLogin") retrofit: Retrofit): UserAuthService {
        return retrofit.create(UserAuthService::class.java)
    }

    @Singleton
    @Provides
    internal fun provideExerciseService(@Named("OunmoApi") retrofit: Retrofit): ExerciseService {
        return retrofit.create(ExerciseService::class.java)
    }
}