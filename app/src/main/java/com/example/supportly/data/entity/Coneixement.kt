package com.example.supportly.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "Coneixement",
    foreignKeys = [
        ForeignKey(
            entity = Usuaris::class,
            parentColumns = arrayOf("id_usuari"),
            childColumns = arrayOf("id_usuari"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Categoria::class,
            parentColumns = arrayOf("id_categoria"),
            childColumns = arrayOf("id_categoria"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Coneixement(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_coneixement")
    val idConeixement: Int = 0,

    @ColumnInfo(name = "id_usuari")
    val idUsuari: Int,

    @ColumnInfo(name = "id_categoria")
    val idCategoria: Int
)
