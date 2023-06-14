package com.example.data.repository

import com.example.data.datasource.local.UserAuthLocalDataSource
import com.example.data.datasource.remote.UserAuthRemoteDataSource
import com.example.domain.entity.LoginGoogleEntity
import com.example.domain.entity.LoginOunmoEntity
import com.example.domain.repository.UserAuthRepository
import com.example.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(
    private val userAuthRemoteDataSource: UserAuthRemoteDataSource,
    private val userAuthLocalDataSource: UserAuthLocalDataSource
) : UserAuthRepository {

    override suspend fun checkLoginStatus(): Boolean {
        return userAuthLocalDataSource.checkLoginStatus()
    }

    override fun ounmoLogin(authToken: String): Flow<Result<LoginOunmoEntity>> {
        return userAuthRemoteDataSource.ounmoLogin(authToken)
    }

    override fun googleLogin(authCode: String): Flow<Result<LoginGoogleEntity>> {
        return userAuthRemoteDataSource.googleLogin(authCode)
    }

//    override fun logout(): Flow<Result<String>> {
//        return userAuthRemoteDataSource.logout()
//    }

    override suspend fun registerAuthToken(accessToken: String, refreshToken: String) {
        return userAuthLocalDataSource.registerAuthToken(accessToken, refreshToken)
    }

    override suspend fun deleteAuthToken() {
        return userAuthLocalDataSource.deleteAuthToken()
    }
}