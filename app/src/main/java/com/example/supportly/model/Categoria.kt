package com.example.supportly.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoria")
data class Categoria(
    @PrimaryKey val id_categoria: Int,
    val nom: String
)