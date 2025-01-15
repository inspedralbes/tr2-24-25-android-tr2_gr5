package com.example.supportly.ui.view

import android.os.Process
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.supportly.R
import com.example.supportly.model.Usuari
import com.example.supportly.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Lista de imágenes para selección
private val profileImageOptions = listOf(
    R.drawable.bulbasour,
    R.drawable.charmader,
    R.drawable.squirtel,
    R.drawable.unnamed,
    R.drawable.volleyball,
    R.drawable.futbol,
    R.drawable.futbolamericano
)

fun safePainterResource(id: Int, fallback: Int): Int {
    return try {
        id // Retorna el ID original si no hay errores
    } catch (e: Exception) {
        fallback // Retorna el ID de respaldo en caso de error
    }
}

@Composable
fun ProfileScreen(email: String, password: String) {
    var user by remember { mutableStateOf<Usuari?>(null) } // Variable para almacenar los datos del usuario
    var selectedImage by rememberSaveable { mutableStateOf(R.drawable.ic_person) }
    var savedImage by rememberSaveable { mutableStateOf(R.drawable.ic_person) } // Imagen guardada
    var isLoading by remember { mutableStateOf(true) } // Para controlar el estado de carga

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
                // Manejo de errores (podrías mostrar un Toast o algo aquí)
            }
        })
    }

    // Si los datos están siendo cargados, mostramos un mensaje de carga
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center) // Esto centra el CircularProgressIndicator
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // Se asegura que haya espacio entre los elementos
        ) {
            // Imagen de perfil seleccionada
            Image(
                painter = painterResource(id = safePainterResource(savedImage, fallback = R.drawable.ic_person)),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
                    .padding(8.dp)
            )

            // Si el usuario es nulo, mostramos un mensaje de error
            if (user == null) {
                Text(
                    text = "No se encontraron datos para este usuario",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Red
                )
            } else {
                // Nombre y correo
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = user?.nom ?: "Nombre no disponible",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    Text(
                        text = user?.correu_alumne ?: "Correo no disponible",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Estado del usuario
                Text(
                    text = "Estado: En línea",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Green,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Fila de selección de imágenes
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(profileImageOptions.size) { index ->
                        val imageRes = profileImageOptions[index]
                        ImageSelection(
                            imageRes = imageRes,
                            isSelected = selectedImage == imageRes,
                            onClick = { selectedImage = imageRes }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón de guardar la imagen seleccionada
                Button(
                    onClick = {
                        savedImage = selectedImage // Guardamos la imagen seleccionada
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(0.7f)
                        .height(50.dp)
                ) {
                    Text(text = "Guardar", style = MaterialTheme.typography.titleLarge)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón de salir
                Button(
                    onClick = {
                        // Cerrar la aplicación
                        Process.killProcess(Process.myPid()) // Esto cierra la aplicación
                    },
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    Text(text = "Salir", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}

@Composable
fun ImageSelection(imageRes: Int, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.Gray else Color.LightGray
        ),
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp)
            .height(100.dp)
            .width(100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = CircleShape
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Imagen seleccionada",
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )
    }
}
