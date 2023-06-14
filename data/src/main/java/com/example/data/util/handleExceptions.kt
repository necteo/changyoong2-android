package com.example.data.util

import com.example.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import java.net.ConnectException

fun <T> Flow<Result<T>>.handleExceptions(): Flow<Result<T>> {
    return this.catch { throwable ->
        when (throwable) {
            is ConnectException -> {
                // Handle Timeout Error
                emit(Result.Error(Exception(SERVER_CONNECTION_ERROR)))
            }
            is IOException -> {
                // Handle Other Network Error
                emit(Result.Error(Exception(INTERNET_CONNECTION_ERROR)))
            }
            else -> {
                // Handle Other Error
                emit(Result.Error(throwable))
            }
        }
    }
}