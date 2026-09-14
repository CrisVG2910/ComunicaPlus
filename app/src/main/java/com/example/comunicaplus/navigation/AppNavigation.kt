package com.example.comunicaplus.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.comunicaplus.data.UsuarioRepository
import com.example.comunicaplus.model.Usuario
import com.example.comunicaplus.ui.screens.HomeScreen
import com.example.comunicaplus.ui.screens.LoginScreen
import com.example.comunicaplus.ui.screens.RecoverPasswordScreen
import com.example.comunicaplus.ui.screens.RegisterScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    var usuarioActual by remember {
        mutableStateOf<Usuario?>(null)
    }

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN,
        modifier = modifier.fillMaxSize()
    ) {

        /*
         * LOGIN
         */
        composable(Routes.LOGIN) {

            LoginScreen(

                onLoginClick = { correo, contrasena ->

                    val usuario = UsuarioRepository
                        .validarCredenciales(
                            correo = correo,
                            contrasena = contrasena
                        )

                    if (usuario != null) {

                        usuarioActual = usuario

                        navController.navigate(Routes.HOME) {

                            popUpTo(Routes.LOGIN) {
                                inclusive = true
                            }
                        }

                        true

                    } else {

                        false
                    }
                },

                onRegisterClick = {

                    navController.navigate(
                        Routes.REGISTER
                    )
                },

                onRecoverPasswordClick = {

                    navController.navigate(
                        Routes.RECOVER_PASSWORD
                    )
                }
            )
        }

        /*
         * REGISTRO
         */
        composable(Routes.REGISTER) {

            RegisterScreen(

                onRegisterClick = { usuario ->

                    val registrado =
                        UsuarioRepository
                            .registrarUsuario(usuario)

                    if (registrado) {

                        navController.navigate(
                            Routes.LOGIN
                        ) {

                            popUpTo(Routes.REGISTER) {
                                inclusive = true
                            }
                        }
                    }

                    registrado
                },

                onBackToLoginClick = {

                    navController.popBackStack()
                }
            )
        }

        /*
         * RECUPERAR CONTRASEÑA
         */
        composable(Routes.RECOVER_PASSWORD) {

            RecoverPasswordScreen(

                onRecoverPasswordClick = { correo ->

                    UsuarioRepository.existeCorreo(correo)
                },

                onBackToLoginClick = {

                    navController.popBackStack()
                }
            )
        }

        /*
         * HOME
         */
        composable(Routes.HOME) {

            val usuario = usuarioActual

            if (usuario != null) {

                HomeScreen(
                    usuario = usuario,

                    onLogoutClick = {

                        usuarioActual = null

                        navController.navigate(
                            Routes.LOGIN
                        ) {

                            popUpTo(Routes.HOME) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}