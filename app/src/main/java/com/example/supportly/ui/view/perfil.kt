package com.example.supportly.ui.view

import androidx.compose.foundation.Image
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
fun ProfileScreen(navController: NavController, userName: String = "Nombre de Usuario", userEmail: String = "usuario@gmail.com") {
    var selectedImage by rememberSaveable { mutableStateOf(R.drawable.ic_person) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Imagen de perfil seleccionada
        Image(
            painter = painterResource(
                id = safePainterResource(
                    id = selectedImage,
                    fallback = R.drawable.ic_person
                )
            ),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .padding(16.dp)
        )

        // Nombre y correo
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = userEmail,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Fila de selección de imágenes
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
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
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Imagen seleccionada",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun EditProfileScreen(navController: NavController, userName: String, userEmail: String) {
    var selectedImage by rememberSaveable { mutableStateOf(R.drawable.ic_person) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Imagen de perfil seleccionada
        Image(
            painter = painterResource(
                id = safePainterResource(
                    id = selectedImage,
                    fallback = R.drawable.ic_person
                )
            ),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .padding(16.dp)
        )

        // Nombre y correo
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = userEmail,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Fila de selección de imágenes
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
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
    }
}
