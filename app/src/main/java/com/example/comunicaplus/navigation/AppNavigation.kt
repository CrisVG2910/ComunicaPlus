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
import com.example.comunicaplus.model.Usuario
import com.example.comunicaplus.ui.screens.HomeScreen
import com.example.comunicaplus.ui.screens.LoginScreen
import com.example.comunicaplus.ui.screens.RecoverPasswordScreen
import com.example.comunicaplus.ui.screens.RegisterScreen
import com.example.comunicaplus.data.AuthRepository
import com.example.comunicaplus.data.UsuarioRepository
import com.example.comunicaplus.ui.screens.EditProfileScreen
import com.example.comunicaplus.ui.screens.EscribirScreen
import com.example.comunicaplus.ui.screens.HablarScreen

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

        composable(
            Routes.ESCRIBIR
        ) {

            EscribirScreen(

                onBackClick = {

                    navController
                        .popBackStack()
                }
            )
        }

        composable(
            Routes.HABLAR
        ) {

            HablarScreen(

                onBackClick = {

                    navController
                        .popBackStack()
                }
            )
        }

        /*
         * LOGIN
         */
        composable(Routes.LOGIN) {

            LoginScreen(

                onLoginClick = { correo, contrasena ->

                    val resultado =
                        AuthRepository.iniciarSesion(
                            correo = correo,
                            contrasena = contrasena
                        )

                    resultado.onSuccess { usuario ->

                        usuarioActual = usuario

                        navController.navigate(
                            Routes.HOME
                        ) {

                            popUpTo(Routes.LOGIN) {
                                inclusive = true
                            }
                        }
                    }

                    resultado.map {
                        Unit
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

                onRegisterClick = { usuario, contrasena ->

                    val resultado =
                        AuthRepository.registrarUsuario(
                            usuario = usuario,
                            contrasena = contrasena
                        )

                    resultado.onSuccess {

                        /*
                         * Firebase deja al usuario autenticado
                         * después de crear la cuenta.
                         * Cerramos sesión porque nuestro flujo
                         * vuelve al Login.
                         */
                        AuthRepository.cerrarSesion()

                        navController.navigate(
                            Routes.LOGIN
                        ) {

                            popUpTo(Routes.REGISTER) {
                                inclusive = true
                            }
                        }
                    }

                    resultado.map {
                        Unit
                    }
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

                    AuthRepository
                        .recuperarContrasena(correo)
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

                    onEscribirClick = {

                        navController.navigate(
                            Routes.ESCRIBIR
                        )
                    },

                    onHablarClick = {

                        navController.navigate(
                            Routes.HABLAR
                        )
                    },

                    onBuscarDispositivoClick = {
                        // Próximo hito.
                    },

                    onEditProfileClick = {

                        navController.navigate(
                            Routes.EDIT_PROFILE
                        )
                    },

                    onLogoutClick = {

                        AuthRepository
                            .cerrarSesion()

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

        composable(
            Routes.EDIT_PROFILE
        ) {

            val usuario = usuarioActual

            if (usuario != null) {

                EditProfileScreen(
                    usuario = usuario,

                    onSaveClick = {
                            usuarioActualizado ->

                        try {

                            UsuarioRepository
                                .actualizarUsuario(
                                    usuarioActualizado
                                )

                            usuarioActual =
                                usuarioActualizado

                            navController
                                .popBackStack()

                            Result.success(Unit)

                        } catch (e: Exception) {

                            Result.failure(e)
                        }
                    },

                    onDeleteAccountClick = {
                            contrasena ->

                        val resultado =
                            AuthRepository
                                .eliminarCuenta(
                                    contrasena
                                )

                        resultado.onSuccess {

                            usuarioActual = null

                            navController.navigate(
                                Routes.LOGIN
                            ) {

                                popUpTo(
                                    Routes.LOGIN
                                ) {
                                    inclusive = true
                                }
                            }
                        }

                        resultado
                    },

                    onBackClick = {

                        navController
                            .popBackStack()
                    }
                )
            }
        }
    }
}