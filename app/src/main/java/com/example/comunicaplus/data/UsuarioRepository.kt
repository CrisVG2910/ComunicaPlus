package com.example.comunicaplus.data

import com.example.comunicaplus.model.Usuario

object UsuarioRepository {

    /*
     * Array inicial de 5 usuarios
     */
    private val usuariosIniciales = arrayOf(
        Usuario(
            nombre = "Ana Torres",
            correo = "ana@comunicaplus.cl",
            contrasena = "1234"
        ),
        Usuario(
            nombre = "Carlos Soto",
            correo = "carlos@comunicaplus.cl",
            contrasena = "1234"
        ),
        Usuario(
            nombre = "María González",
            correo = "maria@comunicaplus.cl",
            contrasena = "1234"
        ),
        Usuario(
            nombre = "Pedro Vargas",
            correo = "pedro@comunicaplus.cl",
            contrasena = "1234"
        ),
        Usuario(
            nombre = "Sofía Reyes",
            correo = "sofia@comunicaplus.cl",
            contrasena = "1234"
        )
    )

    private val usuarios = usuariosIniciales.toMutableList()

    fun obtenerUsuarios(): List<Usuario> {
        return usuarios.toList()
    }

    fun existeCorreo(correo: String): Boolean {
        return usuarios.any {
            it.correo.equals(correo.trim(), ignoreCase = true)
        }
    }

    fun registrarUsuario(usuario: Usuario): Boolean {

        if (existeCorreo(usuario.correo)) {
            return false
        }

        usuarios.add(usuario)

        return true
    }

    fun validarCredenciales(
        correo: String,
        contrasena: String
    ): Usuario? {

        return usuarios.find {
            it.correo.equals(
                correo.trim(),
                ignoreCase = true
            ) &&
                    it.contrasena == contrasena
        }
    }
}