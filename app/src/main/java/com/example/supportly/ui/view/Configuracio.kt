package com.example.supportly.ui.view

import android.os.Process
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.* // Asegúrate de importar desde material3
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.supportly.R
import com.example.supportly.model.Usuari
import com.example.supportly.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Config(email: String, password: String, navController: NavController) {
    var user by remember { mutableStateOf<Usuari?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Realizar la llamada a la API para obtener los datos del usuario
    LaunchedEffect(email) {
        RetrofitInstance.api.getUsuariPorCorreu(email).enqueue(object : Callback<Usuari> {
            override fun onResponse(call: Call<Usuari>, response: Response<Usuari>) {
                if (response.isSuccessful) {
                    user = response.body() // Guardamos el usuario
                }
                isLoading = false // Terminamos de cargar
            }

            override fun onFailure(call: Call<Usuari>, t: Throwable) {
                isLoading = false
                // Manejo de errores
            }
        })
    }

    // Si los datos están siendo cargados, mostramos un mensaje de carga
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Parte superior: Imagen y datos del usuario
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                // Imagen de perfil
                Image(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                        .padding(8.dp)
                )
            }

            // Datos del usuario
            user?.let {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 12.dp) // Reducido el espacio superior
                ) {
                    // Nombre y apellido (más grande)
                    Text(
                        text = "${it.nom} ${it.cognom}",
                        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 28.sp), // Tamaño un poco más grande
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    // Correo
                    Text(
                        text = it.correu_alumne ?: "Correo no disponible",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp), // Tamaño más grande que el predeterminado
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    // Peticiones acabadas
                    Text(
                        text = "Peticiones acabadas: ${it.peticionsAcabades}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp), // Tamaño más grande que el predeterminado
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    // Likes
                    Text(
                        text = "Likes: ${it.likes}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp), // Tamaño más grande que el predeterminado
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            } ?: run {
                // Mensaje en caso de error al obtener los datos
                Text(
                    text = "No se encontraron datos para este usuario",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botones en la parte inferior
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        // Cerrar sesión
                        Process.killProcess(Process.myPid()) // Cierra la app
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp) // Aumentar el espacio horizontal
                        .padding(bottom = 16.dp) // Separación entre los botones
                ) {
                    Text(text = "Cerrar sesión", fontSize = 18.sp) // Ajuste el tamaño de texto
                }

                Button(
                    onClick = {
                        // Ir a una pantalla de configuración u otra
                        navController.navigate("configuracion") // Reemplaza con la ruta que desees
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp) // Aumentar el espacio horizontal
                ) {
                    Text(text = "Editar Perfil", fontSize = 18.sp) // Ajuste el tamaño de texto
                }
            }
        }
    }
}

