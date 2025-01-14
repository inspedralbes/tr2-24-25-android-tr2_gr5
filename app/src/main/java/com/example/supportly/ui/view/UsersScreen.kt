package com.example.supportly.ui.view

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.itemsIndexed
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
    var filteredList by remember { mutableStateOf<List<Usuari>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var searchText by remember { mutableStateOf("") }

    // Llama al endpoint para obtener los mentores
    LaunchedEffect(Unit) {
        val call = RetrofitInstance.api.getUsuarisPorTipus("ment") // Sólo mentores
        call.enqueue(object : Callback<List<Usuari>> {
            override fun onResponse(call: Call<List<Usuari>>, response: Response<List<Usuari>>) {
                if (response.isSuccessful) {
                    val mentores = response.body() ?: emptyList()
                    // Ordenar mentores por peticionesAcabadas de mayor a menor
                    userList = mentores.sortedByDescending { it.peticionsAcabades }.take(10)
                    filteredList = userList // Inicializa la lista filtrada
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

    // Filtra la lista de usuarios en base al texto de búsqueda
    LaunchedEffect(searchText) {
        filteredList = if (searchText.isEmpty()) {
            userList
        } else {
            userList.filter {
                it.nom.contains(searchText, ignoreCase = true) ||
                        it.cognom.contains(searchText, ignoreCase = true) ||
                        getCorreoByTipus(it).contains(searchText, ignoreCase = true)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Barra de búsqueda
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Buscar mentores...") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 16.sp),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray.copy(alpha = 0.2f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
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
                itemsIndexed(filteredList) { index, user -> // Usa itemsIndexed para obtener el índice
                    val isInTop10 = userList.contains(user)
                    UserCard(user = user, position = index + 1, isInTop10 = isInTop10)
                }
            }
        }
    }
}

// Tarjeta para mostrar la información de un usuario
@Composable
fun UserCard(user: Usuari, position: Int, isInTop10: Boolean) {
    androidx.compose.material.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp // Elevación de Material 2
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Muestra el número de posición con colores distintivos para el top 10
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = if (isInTop10) {
                            when (position) {
                                1 -> Color(0xFFFFD700) // Oro
                                2 -> Color(0xFFC0C0C0) // Plata
                                3 -> Color(0xFFCD7F32) // Bronce
                                else -> Color.LightGray
                            }
                        } else {
                            Color.Gray // Color neutro si no está en el top 10
                        },
                        shape = MaterialTheme.shapes.small
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = position.toString(),
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                    color = if (isInTop10) Color.Black else Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información del usuario
            Column {
                Text(
                    text = "Nombre: ${user.nom} ${user.cognom}",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "Tipo: ${user.tipus.capitalize()}",
                    style = MaterialTheme.typography.body2,
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



