package com.example.supportly.ui.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.supportly.model.Usuari
import com.example.supportly.network.RetrofitInstance.api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ChatsScreen(sender: String, navController: NavController) {
    var messageText by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var users by remember { mutableStateOf<List<Usuari>>(emptyList()) }

    // El 'navController' ya se pasa como parámetro, no necesitas crear uno nuevo.
    val navController = rememberNavController()

    // Configuración de NavHost
    NavHost(
        navController = navController,
        startDestination = "chats_screen"
    ) {
        composable("chats_screen") {
            // Pantalla de chats (aquí va tu contenido)
            Column(modifier = Modifier.fillMaxSize()) {
                // Agregar contenido de la pantalla de chats si lo deseas
            }
        }

        composable("tusmuertos") {
            // Pantalla de tus muertos
            TusMuertosScreen()
        }
    }

    // LaunchedEffect para hacer la llamada a la API y obtener usuarios
    LaunchedEffect(Unit) {
        api.usuaris().enqueue(object : Callback<List<Usuari>> {
            override fun onResponse(
                call: Call<List<Usuari>>,
                response: Response<List<Usuari>>
            ) {
                if (response.isSuccessful) {
                    val filteredUsers = response.body()?.filter {
                        it.correu_alumne != null && it.correu_alumne.isNotEmpty()
                    } ?: emptyList()
                    users = filteredUsers
                }
            }

            override fun onFailure(call: Call<List<Usuari>>, t: Throwable) {
                println("Error en la llamada: ${t.message}")
            }
        })
    }

    // Filtrar usuarios
    val filteredUsers = users.filter {
        it.nom.contains(searchQuery, ignoreCase = true)
    }

    // UI principal de la pantalla "ChatsScreen"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Buscar usuari per nom",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                singleLine = true
            )

            IconButton(
                onClick = {
                    // Navegar a "TusMuertosScreen"
                    navController.navigate("tusmuertos") {
                        // Eliminamos la pantalla actual "chats_screen" de la pila
                        popUpTo("chats_screen") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Ir a la siguiente pantalla"
                )
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredUsers) { user ->
                UserItemWithIcon(user = user, navController = navController)
            }
        }
    }
}
@Composable
fun UserItemWithIcon(user: Usuari, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, MaterialTheme.colors.onSurface)
            .padding(16.dp)
            .clickable {
                // Al hacer clic, navegar a "TusMuertosScreen" reemplazando la pantalla actual
                navController.navigate("tusmuertos") {
                    // Eliminamos la pantalla actual "chats_screen" de la pila
                    popUpTo("chats_screen") { inclusive = true }
                    launchSingleTop = true
                }
            }
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Nom: ${user.nom} ${user.cognom}")
            Text(text = "Correu Alumne: ${user.correu_alumne}")
        }
    }
}

