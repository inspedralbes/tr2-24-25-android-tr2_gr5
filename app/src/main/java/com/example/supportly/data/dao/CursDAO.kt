package com.example.supportly.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.supportly.data.entity.*

/**
 * DAO for `Curs` entity
 */
@Dao
interface CursDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(curs: Curs)

    @Update
    suspend fun update(curs: Curs)

    @Delete
    suspend fun delete(curs: Curs)

    @Query("SELECT * FROM Curs")
    fun getAllCursos(): LiveData<List<Curs>>

    @Query("SELECT * FROM Curs WHERE id_Curs = :id")
    suspend fun getCursById(id: Int): Curs?
}