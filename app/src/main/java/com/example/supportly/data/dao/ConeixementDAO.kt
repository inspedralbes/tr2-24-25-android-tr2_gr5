package com.example.myapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.supportly.data.entity.*

/**
 * DAO for `Coneixements` entity
 */
@Dao
interface ConeixementDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coneixement: Coneixement)

    @Update
    suspend fun update(coneixement: Coneixement)

    @Delete
    suspend fun delete(coneixement: Coneixement)

    @Query("SELECT * FROM Coneixement")
    fun getAllConeixements(): LiveData<List<Coneixement>>

    @Query("SELECT * FROM Coneixement WHERE id_Coneixement = :id")
    suspend fun getConeixementById(id: Int): Coneixement?
}

