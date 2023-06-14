package com.example.domain.usecase

import com.example.domain.entity.LoginGoogleEntity
import com.example.domain.repository.UserAuthRepository
import com.example.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoogleLoginUseCase @Inject constructor(
    private val userAuthRepository: UserAuthRepository
) {
    operator fun invoke(authCode: String): Flow<Result<LoginGoogleEntity>> {
        return userAuthRepository.googleLogin(authCode)
    }
}