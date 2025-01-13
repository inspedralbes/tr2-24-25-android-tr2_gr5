package com.example.supportly.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.supportly.data.entity.*
/**
 * DAO for `Peticio` entity
 */
@Dao
interface PeticioDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(peticio: Peticio)

    @Update
    suspend fun update(peticio: Peticio)

    @Delete
    suspend fun delete(peticio: Peticio)

    @Query("SELECT * FROM Peticio")
    fun getAllPeticions(): LiveData<List<Peticio>>

    @Query("SELECT * FROM Peticio WHERE id_peticio = :id")
    suspend fun getPeticioById(id: Int): Peticio?
}
