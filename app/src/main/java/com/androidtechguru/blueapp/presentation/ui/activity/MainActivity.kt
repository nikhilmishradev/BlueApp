package com.androidtechguru.blueapp.presentation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.androidtechguru.blueapp.R
import com.androidtechguru.blueapp.presentation.ui.screens.MainScreen
import com.androidtechguru.blueapp.presentation.ui.screens.SplashScreen
import com.androidtechguru.blueapp.presentation.ui.theme.BlueAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlueAppTheme {
                App()
            }
        }
    }
}


@Composable
fun App() {
    val navController = rememberNavController()
    val splash = stringResource(R.string.splash)
    val main = stringResource(R.string.main)
    
    NavHost(navController = navController, startDestination = splash) {
        composable(route = splash) {
            SplashScreen {
                navController.navigate(main) {
                    popUpTo(splash) { inclusive = true }
                }
            }
        }
        composable(main) {
            MainScreen()
        }
    }
}