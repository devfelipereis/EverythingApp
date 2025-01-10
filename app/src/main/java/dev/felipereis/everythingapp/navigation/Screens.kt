package dev.felipereis.everythingapp.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data object DrawWithContent : Screen()
}