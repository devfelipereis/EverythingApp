package dev.felipereis.everythingapp.features.home.presentation

import dev.felipereis.everythingapp.navigation.Screen

val menuItems = listOf(
    MenuItem(
        title = "drawWithContent modifier",
        description = "An example of how to use the drawWithContent modifier",
        destination = Screen.DrawWithContent,
    ),
).sortedBy { it.title }