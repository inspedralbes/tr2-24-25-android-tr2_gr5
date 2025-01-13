package com.example.supportly.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "Usuaris",
    foreignKeys = [
        ForeignKey(
            entity = Curs::class,
            parentColumns = arrayOf("id_curs"),
            childColumns = arrayOf("id_curs"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Usuaris(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_usuari")
    val idUsuari: Int = 0,

    @ColumnInfo(name = "nom")
    val nom: String,

    @ColumnInfo(name = "cognom")
    val cognom: String,

    @ColumnInfo(name = "correu_alumne")
    val correuAlumne: String?,

    @ColumnInfo(name = "correu_tutor")
    val correuTutor: String?,

    @ColumnInfo(name = "correu_profe")
    val correuProfe: String,

    @ColumnInfo(name = "id_curs")
    val idCurs: Int?,

    @ColumnInfo(name = "contrasenya")
    val contrasenya: String,

    @ColumnInfo(name = "tipus")
    val tipus: String, // Se puede representar como String (enum 'alum', 'prof', 'ment')

    @ColumnInfo(name = "imatge_usuari_ruta")
    val imatgeUsuariRuta: String? = null,

    @ColumnInfo(name = "likes")
    val likes: Int = 0,

    @ColumnInfo(name = "dislikes")
    val dislikes: Int = 0,

    @ColumnInfo(name = "valid_tut_aula")
    val validTutAula: Boolean = false,

    @ColumnInfo(name = "valid_tut_legal")
    val validTutLegal: Boolean? = null,

    @ColumnInfo(name = "peticionsAcabades")
    val peticionsAcabades: Int = 0
)
