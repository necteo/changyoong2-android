package com.example.domain.util

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val throwable: Throwable, val statusCode: Int? = 0) : Result<Nothing>()
}