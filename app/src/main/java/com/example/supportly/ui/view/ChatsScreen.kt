package com.example.supportly.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.supportly.model.PeticioResponse
import com.example.supportly.model.Usuari
import com.example.supportly.network.RetrofitInstance
import com.example.supportly.network.RetrofitInstance.api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ChatsScreen(sender: String, navController: NavController) {
    var messageText by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var users by remember { mutableStateOf<List<Usuari>>(emptyList()) }

    LaunchedEffect(Unit) {
        api.usuaris().enqueue(object : Callback<List<Usuari>> {
            override fun onResponse(call: Call<List<Usuari>>, response: Response<List<Usuari>>) {
                if (response.isSuccessful) {

                    val filteredUsers = response.body()?.filter {
                        it.correu_alumne != null && it.correu_alumne.isNotEmpty()
                    } ?: emptyList()
                    // Si la respuesta es exitosa, actualizamos la lista de usuarios
                    users = filteredUsers
                }
            }

            override fun onFailure(call: Call<List<Usuari>>, t: Throwable) {
                // Manejo de errores en caso de que la llamada falle
                println("Error en la llamada: ${t.message}")
            }
        })
    }

    // Filtrar usuarios por el nombre ingresado
    val filteredUsers = users.filter {
        it.nom.contains(searchQuery, ignoreCase = true)
    }

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

        // TextField para ingresar el nombre y filtrar los usuarios
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            singleLine = true
        )

        // Mostrar la lista de usuarios filtrada
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredUsers) { user ->
                UserItem(user = user, navController = navController) // Pasa el objeto 'user', no la clase
            }
        }
    }
}

@Composable
fun UserItem(user: Usuari, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, MaterialTheme.colors.onSurface)
            .padding(16.dp)
            .clickable {
                navController.navigate("usuari_chat/${user.nom}")
            }
    ) {
        Text(text = "Nom: ${user.nom} ${user.cognom}")
        Text(text = "Correu Alumne: ${user.correu_alumne}")
    }
}

