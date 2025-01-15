package com.example.supportly.network

import androidx.room.Query
import com.example.supportly.model.Categoria
import com.example.supportly.model.LoginRequest
import com.example.supportly.model.LoginResponse
import com.example.supportly.model.Curs
import com.example.supportly.model.PeticioResponse
import com.example.supportly.model.Usuari
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.concurrent.TimeUnit


object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:3000/"
//http://tr2g5.dam.inspedralbes.cat:23412

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: Mentoria by lazy {
        retrofit.create(Mentoria::class.java)
    }
}

interface Mentoria {
    @GET("peticionActivada")
    fun peticion(): Call<List<PeticioResponse>>

    @GET("categoria")
    fun categoria(): Call<List<Categoria>>

    @POST("peticion")
    fun crearPeticion(@Body nuevaPeticion: PeticioResponse): Call<ResponseBody>

    @GET("peticion/{id}")
    fun getPeticionID(@Path("id") id: Int): Call<PeticioResponse>

    @PUT("peticion/{id}/asignada")
    fun asignarUsuario(
        @Path("id") id: Int, // ID de la petición
        @Body usuaroAsignado: PeticioResponse // Se envía un mapa con id_usuari_asignat
    ): Call<ResponseBody>

    @POST("mentors")
    fun registerMentor(@Body mentor: Usuari): Call<ResponseBody>

    @POST("login")
    fun login(@Body credentials: LoginRequest): Call<LoginResponse>

    @GET("curs")
    fun curs(): Call<List<Curs>>

    @GET("usuaris/{tipus}")
    fun getUsuarisPorTipus(@Path("tipus") tipus: String): Call<List<Usuari>>

    @GET("usuaris")
    fun getUsuaris(): Call<List<Usuari>>
}
