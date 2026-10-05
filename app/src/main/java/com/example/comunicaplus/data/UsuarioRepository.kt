package com.example.comunicaplus.data

import com.example.comunicaplus.model.Usuario
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

object UsuarioRepository {

    private val db = Firebase.firestore

    private val usuariosRef =
        db.collection("usuarios")

    suspend fun crearUsuario(
        usuario: Usuario
    ) {
        require(usuario.uid.isNotBlank())

        usuariosRef
            .document(usuario.uid)
            .set(usuario)
            .await()
    }

    suspend fun obtenerUsuario(
        uid: String
    ): Usuario? {

        val documento =
            usuariosRef
                .document(uid)
                .get()
                .await()

        return documento.toObject(
            Usuario::class.java
        )
    }

    suspend fun actualizarUsuario(
        usuario: Usuario
    ) {
        require(usuario.uid.isNotBlank())

        usuariosRef
            .document(usuario.uid)
            .set(usuario)
            .await()
    }

    suspend fun eliminarUsuario(
        uid: String
    ) {
        usuariosRef
            .document(uid)
            .delete()
            .await()
    }
}