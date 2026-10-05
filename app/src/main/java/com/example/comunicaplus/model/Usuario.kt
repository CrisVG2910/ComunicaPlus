package com.example.comunicaplus.model

data class Usuario(
    var uid: String = "",
    var nombre: String = "",
    var correo: String = "",
    var nivelAuditivo: String = "Leve",
    var metodoComunicacion: String = "Texto",
    var textoGrande: Boolean = false,
    var vibracion: Boolean = false
)