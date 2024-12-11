package com.example.supportly.model

data class Peticio (
    val nom_Peticio: String,
    val descripcio: String,
)

data class PeticioResponse(
    val peticions: List<Peticio>
)