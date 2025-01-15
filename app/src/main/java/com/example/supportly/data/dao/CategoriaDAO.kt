package com.example.supportly.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.supportly.data.entity.*

/**
 * DAO for `Categoria` entity
 */
@Dao
interface CategoriaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoria: Categoria)

    @Update
    suspend fun update(categoria: Categoria)

    @Delete
    suspend fun delete(categoria: Categoria)

    @Query("SELECT * FROM Categoria")
    fun getAllCategorias(): LiveData<List<Categoria>>

    @Query("SELECT * FROM Categoria WHERE id_Categoria = :id")
    suspend fun getCategoriaById(id: Int): Categoria?
}
