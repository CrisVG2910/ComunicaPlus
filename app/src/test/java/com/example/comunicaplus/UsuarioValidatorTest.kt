package com.example.comunicaplus

import com.example.comunicaplus.util.UsuarioValidator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class UsuarioValidatorTest {

    @Test
    fun registro_correcto_no_retorna_error() {

        val resultado =
            UsuarioValidator.validarRegistro(
                nombre = "Kevin Muñoz",
                correo = "kevin@correo.cl",
                contrasena = "123456",
                confirmarContrasena = "123456"
            )

        assertNull(resultado)
    }

    @Test
    fun registro_con_campos_vacios_retorna_error() {

        val resultado =
            UsuarioValidator.validarRegistro(
                nombre = "",
                correo = "kevin@correo.cl",
                contrasena = "123456",
                confirmarContrasena = "123456"
            )

        assertEquals(
            "Debes completar todos los campos.",
            resultado
        )
    }

    @Test
    fun registro_con_correo_invalido_retorna_error() {

        val resultado =
            UsuarioValidator.validarRegistro(
                nombre = "Kevin Muñoz",
                correo = "correo-invalido",
                contrasena = "123456",
                confirmarContrasena = "123456"
            )

        assertEquals(
            "Debes ingresar un correo electrónico válido.",
            resultado
        )
    }

    @Test
    fun registro_con_contrasenas_distintas_retorna_error() {

        val resultado =
            UsuarioValidator.validarRegistro(
                nombre = "Kevin Muñoz",
                correo = "kevin@correo.cl",
                contrasena = "123456",
                confirmarContrasena = "654321"
            )

        assertEquals(
            "Las contraseñas no coinciden.",
            resultado
        )
    }

    @Test
    fun registro_con_contrasena_corta_retorna_error() {

        val resultado =
            UsuarioValidator.validarRegistro(
                nombre = "Kevin Muñoz",
                correo = "kevin@correo.cl",
                contrasena = "12345",
                confirmarContrasena = "12345"
            )

        assertEquals(
            "La contraseña debe contener al menos 6 caracteres.",
            resultado
        )
    }

    @Test
    fun login_con_datos_completos_es_valido() {

        val resultado =
            UsuarioValidator.validarLogin(
                correo = "kevin@correo.cl",
                contrasena = "123456"
            )

        assertTrue(resultado)
    }

    @Test
    fun login_sin_correo_no_es_valido() {

        val resultado =
            UsuarioValidator.validarLogin(
                correo = "",
                contrasena = "123456"
            )

        assertFalse(resultado)
    }

    @Test
    fun login_sin_contrasena_no_es_valido() {

        val resultado =
            UsuarioValidator.validarLogin(
                correo = "kevin@correo.cl",
                contrasena = ""
            )

        assertFalse(resultado)
    }
}