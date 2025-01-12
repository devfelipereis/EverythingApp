package dev.felipereis.everythingapp.features.mvi_example

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isAuthenticating: Boolean = false,
    val error: AuthenticationError? = null
)