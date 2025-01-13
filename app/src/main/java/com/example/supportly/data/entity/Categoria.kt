package com.example.supportly.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categoria")
data class Categoria(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_categoria")
    val idCategoria: Int = 0,

    @ColumnInfo(name = "nom")
    val nom: String
)
