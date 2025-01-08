package com.example.supportly.model


data class Usuari(
    val nom: String,
    val cognom: String,
    val correu_alumne: String,
    val correu_profe: String,
    val contrasenya: String,
    val id_curs: Int
)