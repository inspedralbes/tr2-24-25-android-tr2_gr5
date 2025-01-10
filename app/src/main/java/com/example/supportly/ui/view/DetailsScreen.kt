package com.example.supportly.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.supportly.model.PeticioResponse
import com.example.supportly.network.RetrofitInstance.api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

@Composable
fun DetailsScreen(peticionId: Int) {
    var peticionDetails: PeticioResponse? by remember { mutableStateOf(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = peticionId) {
        try {
            val response = withContext(Dispatchers.IO) {
                api.getPeticionID(peticionId).execute()
            }

            if (response.isSuccessful) {
                peticionDetails = response.body()
            } else {
                error = "No se encontraron detalles para esta petición"
            }
        } catch (e: IOException) {
            error = "Error de red: ${e.message}"
        } catch (e: Exception) {
            error = "Error inesperado: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else if (error != null) {
        Text("Error: $error")
    } else {
        // Mostrar los detalles de la petición
        peticionDetails?.let {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Nombre: ${it.nom_peticio}")
                Text("Descripción: ${it.descripcio}")
                Text("Usuario encargado: ${it.id_usuari_asignat}")
                // Puedes agregar más campos aquí
            }
        }
    }
}
