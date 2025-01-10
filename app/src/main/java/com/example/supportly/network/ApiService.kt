package com.example.supportly.network

import androidx.room.Query
import com.example.supportly.model.Categoria
import com.example.supportly.model.PeticioResponse
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:3000/"


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
}
