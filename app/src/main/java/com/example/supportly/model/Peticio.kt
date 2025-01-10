package com.example.supportly.model

data class PeticioResponse(
    val id_peticio: Int?,
    val nom_peticio: String,
    val id_usuari: Int,
    val descripcio: String,
    val id_categoria: Int,
    val id_usuari_asignat: Int?


)
