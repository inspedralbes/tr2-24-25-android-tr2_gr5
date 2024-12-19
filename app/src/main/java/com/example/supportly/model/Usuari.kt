package com.example.supportly.model


data class Usuari(
    val nom: String,
    val correu_alumne: String,
    val correu_profe: String,
    val correu_tutor: String,
    val contrasenya: String,
    val telefon: Int,
    val tipus: TipusUsuari,
    val id_curs: Int,
    val imatge_usuari_ruta: String
)

enum class TipusUsuari{
    alum,
    prof,
    ment
}