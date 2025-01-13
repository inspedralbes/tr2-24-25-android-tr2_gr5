package com.example.supportly.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Valoracio",
    foreignKeys = [
        ForeignKey(
            entity = Peticio::class,
            parentColumns = arrayOf("id_peticio"),
            childColumns = arrayOf("id_peticio"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Usuaris::class,
            parentColumns = arrayOf("id_usuari"),
            childColumns = arrayOf("id_usuari_que_valora"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Usuaris::class,
            parentColumns = arrayOf("id_usuari"),
            childColumns = arrayOf("id_usuari_valorat"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Valoracio(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_valoracio")
    val idValoracio: Int = 0,

    @ColumnInfo(name = "id_peticio")
    val idPeticio: Int,

    @ColumnInfo(name = "id_usuari_que_valora")
    val idUsuariQueValora: Int,

    @ColumnInfo(name = "id_usuari_valorat")
    val idUsuariValorat: Int,

    @ColumnInfo(name = "puntuacio")
    val puntuacio: Int,

    @ColumnInfo(name = "comentari")
    val comentari: String? = null,

    @ColumnInfo(name = "data")
    val data: String
)
