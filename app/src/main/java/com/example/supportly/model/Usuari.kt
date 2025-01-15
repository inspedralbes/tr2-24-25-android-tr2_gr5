package com.example.supportly.model


data class Usuari(
    val nom: String,
    val cognom: String,
    val correu_alumne: String,
    val correu_tutor: String,
    val correu_profe: String,
    val contrasenya: String,
    val id_curs: Int,
    val valid_tut_aula: Int,
    val tipus: String,
    val likes: Int,
    val peticionsAcabades: Int
)

