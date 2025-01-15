package com.example.supportly.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "Resposta",
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
            childColumns = arrayOf("id_usuari"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Resposta(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_resposta")
    val idResposta: Int = 0,

    @ColumnInfo(name = "id_peticio")
    val idPeticio: Int,

    @ColumnInfo(name = "id_usuari")
    val idUsuari: Int,

    @ColumnInfo(name = "id_resposta_ref")
    val idRespostaRef: Int = 0,

    @ColumnInfo(name = "contingut")
    val contingut: String,

    @ColumnInfo(name = "data")
    val data: String
)
