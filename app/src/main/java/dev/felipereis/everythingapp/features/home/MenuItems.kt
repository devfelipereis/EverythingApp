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
    MenuItem(
        title = "HTTP request example",
        description = "An example of how to make HTTP requests",
        destination = Screen.HttpExample,
    ),
    MenuItem(
        title = "LocalContext",
        description = "An example of how to use LocalContext.current",
        destination = Screen.LocalContext,
    ),
).sortedBy { it.title }