package dev.felipereis.everythingapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.felipereis.everythingapp.features.draw_with_content.DrawWithContentScreen
import dev.felipereis.everythingapp.features.home.HomeScreen
import dev.felipereis.everythingapp.features.mvi_example.LoginScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.Home,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable<Screen.Home> {
            HomeScreen(
                onItemClicked = { destination -> navController.navigate(destination) }
            )
        }

        composable<Screen.DrawWithContent> {
            DrawWithContentScreen()
        }

        composable<Screen.Login> {
            LoginScreen()
        }
    }
}