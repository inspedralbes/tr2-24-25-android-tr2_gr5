package com.example.supportly.ui.view

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.PasswordVisualTransformation

// Navigation
import androidx.navigation.NavController

// Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Model
import com.example.supportly.model.Usuari
import com.example.supportly.network.RetrofitInstance

@Composable
fun UsersScreen() {
    var userList by remember { mutableStateOf<List<Usuari>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Llama al endpoint para obtener los mentores
    LaunchedEffect(Unit) {
        val call = RetrofitInstance.api.getUsuarisPorTipus("ment") // Sólo mentores
        call.enqueue(object : Callback<List<Usuari>> {
            override fun onResponse(call: Call<List<Usuari>>, response: Response<List<Usuari>>) {
                if (response.isSuccessful) {
                    val mentores = response.body() ?: emptyList()
                    // Ordenar mentores por peticionesAcabadas de mayor a menor
                    userList = mentores.sortedByDescending { it.peticionsAcabades }.take(10)
                } else {
                    errorMessage = "Error: ${response.code()}"
                }
                isLoading = false
            }

            override fun onFailure(call: Call<List<Usuari>>, t: Throwable) {
                errorMessage = "Error: ${t.message}"
                isLoading = false
            }
        })
    }

    // Diseña la pantalla
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Cargando mentores...",
                style = MaterialTheme.typography.body1,
                color = Color.Gray
            )
        }
    } else if (errorMessage != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = errorMessage ?: "Error desconocido",
                style = MaterialTheme.typography.body1,
                color = Color.Red
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(userList) { user ->
                UserCard(user = user)
            }
        }
    }
}

// Función auxiliar para obtener el correo correcto basado en el `tipus`
fun getCorreoByTipus(user: Usuari): String {
    return when (user.tipus.lowercase()) {
        "ment" -> user.correu_tutor
        "alum" -> user.correu_alumne
        "prof" -> user.correu_profe
        else -> "Correo desconocido"
    }
}

// Tarjeta para mostrar la información de un usuario
@Composable
fun UserCard(user: Usuari) {
    androidx.compose.material.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp // Cambiado a elevation de Material 2
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Nombre: ${user.nom} ${user.cognom}",
                style = MaterialTheme.typography.body1 // Cambiado a body1 de Material 2
            )
            Text(
                text = "Tipo: ${user.tipus.capitalize()}",
                style = MaterialTheme.typography.body2, // Cambiado a body2 de Material 2
                color = Color.DarkGray
            )
            Text(
                text = "Correo: ${getCorreoByTipus(user)}",
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
            Text(
                text = "Peticiones acabadas: ${user.peticionsAcabades}",
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
        }
    }
}


