package com.example.domain.usecase

import com.example.domain.entity.LoginOunmoEntity
import com.example.domain.repository.UserAuthRepository
import com.example.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OunmoLoginUseCase @Inject constructor(
    private val userAuthRepository: UserAuthRepository
) {
    operator fun invoke(authToken: String): Flow<Result<LoginOunmoEntity>> {
        return userAuthRepository.ounmoLogin(authToken)
    }
}