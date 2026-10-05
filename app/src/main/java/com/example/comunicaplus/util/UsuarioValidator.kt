package com.example.comunicaplus.util

object UsuarioValidator {

    fun validarRegistro(
        nombre: String,
        correo: String,
        contrasena: String,
        confirmarContrasena: String
    ): String? {

        return when {

            nombre.isBlank() ||
                    correo.isBlank() ||
                    contrasena.isBlank() ||
                    confirmarContrasena.isBlank() -> {
                "Debes completar todos los campos."
            }

            !correo.contains("@") -> {
                "Debes ingresar un correo electrónico válido."
            }

            contrasena != confirmarContrasena -> {
                "Las contraseñas no coinciden."
            }

            contrasena.length < 6 -> {
                "La contraseña debe contener al menos 6 caracteres."
            }

            else -> null
        }
    }

    fun validarLogin(
        correo: String,
        contrasena: String
    ): Boolean {

        return correo.isNotBlank() &&
                contrasena.isNotBlank()
    }
}