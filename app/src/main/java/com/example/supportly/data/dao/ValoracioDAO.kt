package com.example.supportly.data.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.supportly.data.entity.*

/**
 * DAO for `Valoracio` entity
 */
@Dao
interface ValoracioDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(valoracio: Valoracio)

    @Update
    suspend fun update(valoracio: Valoracio)

    @Delete
    suspend fun delete(valoracio: Valoracio)

    @Query("SELECT * FROM Valoracio")
    fun getAllValoracions(): LiveData<List<Valoracio>>

    @Query("SELECT * FROM Valoracio WHERE id_Valoracio = :id")
    suspend fun getValoracioById(id: Int): Valoracio?
}
