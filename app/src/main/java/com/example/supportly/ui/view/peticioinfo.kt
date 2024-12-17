package com.example.supportly.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.supportly.model.PeticioResponse
import com.example.supportly.network.Mentoria

@Composable
fun peticioinfo(id_peticion: String?, mentoriaService: Mentoria) {
    var datosPeticion by remember { mutableStateOf<PeticioResponse?>(null) }

    LaunchedEffect(id_peticion) {
        if (id_peticion != null) {
            val call = mentoriaService.peticion()
            try {
                val response = call.execute()
                if (response.isSuccessful) {
                    val listaPeticiones = response.body()
                    datosPeticion = listaPeticiones?.find { it.id_peticio.toString() == id_peticion }
                } else {
                    println("Error en la petición: ${response.code()}")
                }
            } catch (e: Exception) {
                println("Excepción: ${e.message}")
            }
        }
    }

    Column {
        if (datosPeticion != null) {
            Text("Título: ${datosPeticion?.descripcio}", style = MaterialTheme.typography.bodyLarge)
        } else {
            Text("Cargando detalles...", style = MaterialTheme.typography.bodyMedium)
        }
    }
}