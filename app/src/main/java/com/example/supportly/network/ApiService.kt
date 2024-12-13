package com.example.supportly.network

import com.example.supportly.model.PeticioResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.Call


object RetrofitInstance {
     private const val BASE_URL = "http://10.0.2.2:3010"

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
    @GET("peticion")
    suspend fun getPeticio(): Call<PeticioResponse>
}