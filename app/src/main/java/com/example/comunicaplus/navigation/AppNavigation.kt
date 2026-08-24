package com.example.comunicaplus.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.comunicaplus.ui.screens.LoginScreen
import com.example.comunicaplus.ui.screens.RecoverPasswordScreen
import com.example.comunicaplus.ui.screens.RegisterScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN,
        modifier = modifier.fillMaxSize()
    ) {

        composable(Routes.LOGIN) {

            LoginScreen(
                onLoginClick = {
                    // Implementaremos el login real más adelante.
                },
                onRegisterClick = {
                    navController.navigate(Routes.REGISTER)
                },
                onRecoverPasswordClick = {
                    navController.navigate(Routes.RECOVER_PASSWORD)
                }
            )
        }

        composable(Routes.REGISTER) {

            RegisterScreen(
                onRegisterClick = {
                    // En el próximo paso guardaremos el usuario.
                },
                onBackToLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.RECOVER_PASSWORD) {

            RecoverPasswordScreen(
                onBackToLoginClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}