package com.example.supportly.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.supportly.data.entity.*

/**
 * DAO for `Usuaris` entity
 */
@Dao
interface UsuariDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuari: Usuaris)

    @Update
    suspend fun update(usuari: Usuaris)

    @Delete
    suspend fun delete(usuari: Usuaris)

    @Query("SELECT * FROM Usuaris")
    fun getAllUsuaris(): LiveData<List<Usuaris>>

    @Query("SELECT * FROM Usuaris WHERE id_usuari = :id")
    suspend fun getUsuariById(id: Int): Usuaris?
}
