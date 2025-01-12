package dev.felipereis.everythingapp.features.mvi_example

sealed interface LoginEffect {
    data object LoginSuccess : LoginEffect
    data class LoginError(val error: AuthenticationError) : LoginEffect
}