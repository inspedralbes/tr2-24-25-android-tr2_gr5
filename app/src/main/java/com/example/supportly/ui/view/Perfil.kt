package com.example.supportly.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.supportly.R // Asegúrate de importar R para acceder a los recursos
import androidx.navigation.NavController

@Composable
fun PerfilScreen(onEdit: () -> Unit = {}) {
    // Información del perfil (puedes modificar esto para que sea dinámico)
    val userName = remember { mutableStateOf("Juan Pérez") }
    val userDescription = remember { mutableStateOf("Desarrollador Android") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Foto de perfil
        Image(
            painter = painterResource(id = R.drawable.ic_person), // Usa el recurso ic_person del drawable
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp)
                .clip(CircleShape)
        )

        // Nombre del usuario
        Text(
            text = userName.value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Descripción del usuario
        Text(
            text = userDescription.value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // Botón de editar perfil
        Button(
            onClick = { onEdit() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text(
                text = "Editar Perfil",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
