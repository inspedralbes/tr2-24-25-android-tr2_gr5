package com.example.supportly.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapp.data.dao.ConeixementDAO
import com.example.supportly.data.dao.*
import com.example.supportly.data.entity.*

@Database(
    entities = [
        Categoria::class,
        Curs::class,
        Usuaris::class,
        Coneixement::class,
        Peticio::class,
        Resposta::class,
        Valoracio::class
    ],
    version = 1,
    exportSchema = true // Exporta el esquema para mantener el historial de versiones de la base de datos
)
abstract class AppDatabase : RoomDatabase() {

    // DAOs
    abstract fun categoriaDao(): CategoriaDAO
    abstract fun cursDao(): CursDAO
    abstract fun usuariDao(): UsuariDAO
    abstract fun coneixementDao(): ConeixementDAO
    abstract fun peticioDao(): PeticioDAO
    abstract fun respostaDao(): RespostaDAO
    abstract fun valoracioDao(): ValoracioDAO
}

