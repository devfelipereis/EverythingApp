package dev.felipereis.everythingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dev.felipereis.everythingapp.navigation.SetupNavGraph
import dev.felipereis.everythingapp.ui.theme.EverythingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            EverythingAppTheme {
                SetupNavGraph(
                    navController = navController,
                )
            }
        }
    }
}