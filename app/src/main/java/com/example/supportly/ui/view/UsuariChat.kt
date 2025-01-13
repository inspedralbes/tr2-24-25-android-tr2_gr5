package com.example.supportly.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import com.example.supportly.model.Usuari
import com.example.supportly.network.RetrofitInstance.api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun UsuariChat(currentUserNom: String, userName: String) {
    var usuarioEncontrado by remember { mutableStateOf<Usuari?>(null) }

    // Llamada a la API para obtener el usuario según el nombre
    LaunchedEffect(userName) {
        api.buscarUsuari(userName).enqueue(object : Callback<Usuari> {
            override fun onResponse(call: Call<Usuari>, response: Response<Usuari>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        usuarioEncontrado = usuario
                    } else {
                        println("Usuario no encontrado")
                    }
                } else {
                    println("Error en la respuesta de la API.")
                }
            }

            override fun onFailure(call: Call<Usuari>, t: Throwable) {
                println("Error en la llamada a la API: ${t.message}")
            }
        })
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (usuarioEncontrado != null) {
            // Si encontramos el usuario, mostramos el chat
            Text("Chat con ${usuarioEncontrado?.nom}", style = MaterialTheme.typography.h5)
            // Aquí podrías agregar la lógica para el chat real
        } else {
            // Mientras cargamos el usuario, mostramos un mensaje de carga
            Text("Cargando información del usuario...")
        }
    }
}


