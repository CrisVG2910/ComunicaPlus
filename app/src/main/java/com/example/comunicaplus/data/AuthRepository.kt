package com.example.comunicaplus.data

import com.example.comunicaplus.model.Usuario
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

object AuthRepository {

    private val auth = Firebase.auth

    suspend fun registrarUsuario(
        usuario: Usuario,
        contrasena: String
    ): Result<Usuario> {

        return try {

            val resultadoAuth =
                auth.createUserWithEmailAndPassword(
                    usuario.correo.trim(),
                    contrasena
                ).await()

            val firebaseUser =
                resultadoAuth.user
                    ?: throw IllegalStateException(
                        "No se pudo obtener el usuario creado."
                    )

            val perfil = usuario.copy(
                uid = firebaseUser.uid,
                correo = firebaseUser.email
                    ?: usuario.correo.trim()
            )

            try {

                UsuarioRepository
                    .crearUsuario(perfil)

            } catch (e: Exception) {

                /*
                 * Si Auth se creó pero Firestore falla,
                 * eliminamos la cuenta recién creada
                 * para no dejar datos inconsistentes.
                 */
                firebaseUser.delete().await()

                throw e
            }

            Result.success(perfil)

        } catch (e: Exception) {

            Result.failure(
                mapearError(e)
            )
        }
    }

    suspend fun iniciarSesion(
        correo: String,
        contrasena: String
    ): Result<Usuario> {

        return try {

            val resultadoAuth =
                auth.signInWithEmailAndPassword(
                    correo.trim(),
                    contrasena
                ).await()

            val uid =
                resultadoAuth.user?.uid
                    ?: throw IllegalStateException(
                        "No se pudo obtener el usuario autenticado."
                    )

            val usuario =
                UsuarioRepository
                    .obtenerUsuario(uid)
                    ?: throw IllegalStateException(
                        "No se encontró el perfil del usuario."
                    )

            Result.success(usuario)

        } catch (e: Exception) {

            Result.failure(
                mapearError(e)
            )
        }
    }

    suspend fun recuperarContrasena(
        correo: String
    ): Result<Unit> {

        return try {

            auth.sendPasswordResetEmail(
                correo.trim()
            ).await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(
                mapearError(e)
            )
        }
    }

    fun cerrarSesion() {
        auth.signOut()
    }

    fun obtenerUidActual(): String? {
        return auth.currentUser?.uid
    }

    private fun mapearError(
        error: Exception
    ): Exception {

        return when (error) {

            is FirebaseAuthUserCollisionException ->
                IllegalStateException(
                    "El correo ingresado ya está registrado."
                )

            is FirebaseAuthWeakPasswordException ->
                IllegalStateException(
                    "La contraseña no cumple los requisitos de seguridad."
                )

            is FirebaseAuthInvalidCredentialsException,
            is FirebaseAuthInvalidUserException ->
                IllegalStateException(
                    "Correo o contraseña incorrectos."
                )

            is FirebaseNetworkException ->
                IllegalStateException(
                    "No fue posible conectar con Firebase. Revisa tu conexión a internet."
                )

            else ->
                IllegalStateException(
                    error.localizedMessage
                        ?: "Ocurrió un error inesperado."
                )
        }
    }
}