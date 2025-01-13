package com.example.supportly.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Curs")
data class Curs(
    @PrimaryKey
    @ColumnInfo(name = "id_curs")
    val idCurs: Int,

    @ColumnInfo(name = "numero_curs")
    val numeroCurs: String, // ENUM se puede representar como String

    @ColumnInfo(name = "nom_curs")
    val nomCurs: String // ENUM como String también
)
