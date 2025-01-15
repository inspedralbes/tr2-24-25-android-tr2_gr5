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
fun ProfileScreen(navController: NavController, userName: String = "usuario@gmail.com", userEmail: String = "") {
    var selectedImage by rememberSaveable { mutableStateOf(R.drawable.ic_person) }
    var savedImage by rememberSaveable { mutableStateOf(R.drawable.ic_person) } // Imagen guardada

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
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

        // Nombre y correo
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 12.dp)
            )
            Text(
                text = userEmail,
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
