package com.example.presentation.ui.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GoogleLoginUseCase
import com.example.domain.usecase.OunmoLoginUseCase
import com.example.domain.util.Result
import com.example.presentation.ui.base.BaseViewModel
import com.example.presentation.util.INTERNET_CONNECTION_ERROR
import com.example.presentation.util.SERVER_CONNECTION_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val ounmoLoginUseCase: OunmoLoginUseCase
) : BaseViewModel() {

    private val _googleLoginEvent = MutableSharedFlow<Unit>()
    val googleLoginEvent: SharedFlow<Unit> = _googleLoginEvent.asSharedFlow()

    private val _navigateToRegisterEvent = MutableSharedFlow<Unit>()
    val navigateToRegisterEvent = _navigateToRegisterEvent.asSharedFlow()

    fun googleLogin() = viewModelScope.launch {
        _googleLoginEvent.emit(Unit)
    }

    fun googleLogin(authCode: String) = viewModelScope.launch {
        googleLoginUseCase(authCode = authCode)
            .onEach { result ->
                setLoading(result is Result.Loading)
            }.collect { result ->
                when (result) {
                    is Result.Loading -> return@collect
                    is Result.Success -> {
                        ounmoLogin(result.data.accessToken)
                    }

                    is Result.Error -> {
                        when (result.throwable.message) {
                            SERVER_CONNECTION_ERROR -> {
                                Log.w("LoginViewModel", "login_fail_by_server_error")
                            }
                            INTERNET_CONNECTION_ERROR -> {
                                Log.w("LoginViewModel", "login_fail_by_internet_error")
                            }
                            else -> {
                                Log.w("LoginViewModel", "login_fail")
                            }
                        }
                    }
                }
            }
    }


    fun ounmoLogin(accessToken: String) = viewModelScope.launch {
        ounmoLoginUseCase(authToken = accessToken)
            .onEach { result ->
                setLoading(result is Result.Loading)
            }.collect { result ->
                when (result) {
                    is Result.Loading -> return@collect
                    is Result.Success -> {
                        if (result.data.isNewUser) {
                            // _navigateToRegisterBottomSheetEvent.emit(Unit)
                            Log.d("LoginViewModel", "is new user")
                            _navigateToRegisterEvent.emit(Unit)
                        } else {
                            // createAndRegisterNotificationToken()
                            Log.d("LoginViewModel", "is not new user")
                        }
                    }
                    is Result.Error -> {
                        when (result.throwable.message) {
                            SERVER_CONNECTION_ERROR -> {
                                Log.w("LoginViewModel", "login_fail_by_server_error")
                            }
                            INTERNET_CONNECTION_ERROR -> {
                                Log.w("LoginViewModel", "login_fail_by_internet_error")
                            }
                            else -> {
                                Log.w("LoginViewModel", "login_fail")
                            }
                        }
                    }
                }
            }
    }

}