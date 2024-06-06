package com.zhixue.lite

import androidx.annotation.MainThread
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhixue.lite.core.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZhixueLiteActivityViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var loginState: LoginState by mutableStateOf(LoginState.Loading)
        private set

    private var isInitialized = false

    @MainThread
    fun initialize() {
        if (isInitialized) {
            return
        } else {
            isInitialized = true
        }
        // 启动协程检查登录状态
        viewModelScope.launch {
            runCatching {
                val grantTicket = userRepository.getGrantTicket()
                userRepository.userLogin(grantTicket)
            }.onSuccess {
                loginState = LoginState.LoggedIn
            }.onFailure {
                loginState = LoginState.NotLoggedIn
            }
        }
    }
}

sealed interface LoginState {
    data object Loading : LoginState
    data object LoggedIn : LoginState
    data object NotLoggedIn : LoginState
}