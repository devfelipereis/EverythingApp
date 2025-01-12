package dev.felipereis.everythingapp.features.home

import dev.felipereis.everythingapp.navigation.Screen

val menuItems = listOf(
    MenuItem(
        title = "drawWithContent modifier",
        description = "An example of how to use the drawWithContent modifier",
        destination = Screen.DrawWithContent,
    ),
    MenuItem(
        title = "MVI example",
        description = "An example of how to use the MVI pattern",
        destination = Screen.Login,
    ),
).sortedBy { it.title }