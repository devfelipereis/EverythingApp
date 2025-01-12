package dev.felipereis.everythingapp.features.mvi_example

sealed interface LoginEvent {
    data object LoginRequested : LoginEvent
    data class EmailChanged(val email: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
}