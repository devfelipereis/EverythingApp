package dev.felipereis.everythingapp.features.mvi_example

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.fold
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginState, LoginEvent, LoginEffect>(LoginState()) {
    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.LoginRequested -> onLogin(_uiState.value.email, _uiState.value.password)
            is LoginEvent.EmailChanged -> onEmailChanged(event.email)
            is LoginEvent.PasswordChanged -> onPasswordChanged(event.password)
        }
    }

    private fun onLogin(email: String, password: String) {
        _uiState.update { it.copy(isAuthenticating = true) }

        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase(email, password)
                .fold(
                    success = {
                        addEffect(LoginEffect.LoginSuccess)
                        _uiState.update { it.copy(isAuthenticating = false) }
                    },
                    failure = { error ->
                        addEffect(LoginEffect.LoginError(error))
                        _uiState.update { it.copy(isAuthenticating = false) }
                    }
                )
        }
    }

    private fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    private fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password) }
    }
}