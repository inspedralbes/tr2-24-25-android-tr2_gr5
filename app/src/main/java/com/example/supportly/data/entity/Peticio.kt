package com.example.supportly.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "Peticio",
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
        ),
        ForeignKey(
            entity = Usuaris::class,
            parentColumns = arrayOf("id_usuari"),
            childColumns = arrayOf("id_usuari_asignat"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Peticio(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_peticio")
    val idPeticio: Int = 0,

    @ColumnInfo(name = "id_usuari")
    val idUsuari: Int,

    @ColumnInfo(name = "id_usuari_asignat")
    val idUsuariAsignat: Int? = null,

    @ColumnInfo(name = "id_categoria")
    val idCategoria: Int,

    @ColumnInfo(name = "nom_peticio")
    val nomPeticio: String,

    @ColumnInfo(name = "descripcio")
    val descripcio: String,

    @ColumnInfo(name = "activado")
    val activado: Boolean = false,

    @ColumnInfo(name = "data")
    val data: String // Puedes utilizar un `String` para la fecha
)
