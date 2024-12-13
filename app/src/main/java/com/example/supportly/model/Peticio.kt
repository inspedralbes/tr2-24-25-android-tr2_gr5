package com.example.supportly.model

data class PeticioResponse(
    val peticions: List<Peticion>
)


data class Peticion(
    val nom_peticio: String,
    val descripcio: String
)
