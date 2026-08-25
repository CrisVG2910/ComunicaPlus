package com.example.comunicaplus.model

data class Usuario(
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val nivelAuditivo: String = "Leve",
    val metodoComunicacion: String = "Texto",
    val textoGrande: Boolean = false,
    val vibracion: Boolean = false
)