package com.example.supportly.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.supportly.data.entity.*

/**
 * DAO for `Resposta` entity
 */
@Dao
interface RespostaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resposta: Resposta)

    @Update
    suspend fun update(resposta: Resposta)

    @Delete
    suspend fun delete(resposta: Resposta)

    @Query("SELECT * FROM Resposta")
    fun getAllRespostes(): LiveData<List<Resposta>>

    @Query("SELECT * FROM Resposta WHERE id_Resposta = :id")
    suspend fun getRespostaById(id: Int): Resposta?
}
