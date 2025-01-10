package dev.felipereis.everythingapp.features.home.presentation

import dev.felipereis.everythingapp.navigation.Screen

data class MenuItem(
    val title: String,
    val description: String,
    val destination: Screen,
)