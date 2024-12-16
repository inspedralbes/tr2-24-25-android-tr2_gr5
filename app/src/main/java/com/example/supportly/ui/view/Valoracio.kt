package com.example.supportly.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.supportly.R

@Composable
fun ValoracioScreen(onBackPressed: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Botón de retroceso
        IconButton(
            onClick = { onBackPressed() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = "Volver"
            )
        }

        // Título
        Text(
            text = "VALORACIONES DE MENTOR",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        // Lista de mentores con valoraciones
        Column(modifier = Modifier.fillMaxWidth()) {
            repeat(3) { // Ajusta el número de mentores
                MentorRatingItem()
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Espaciador flexible
    }
}

@Composable
fun MentorRatingItem() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Icono del mentor
        Icon(
            painter = painterResource(id = R.drawable.ic_person),
            contentDescription = "Mentor",
            modifier = Modifier
                .size(48.dp)
                .padding(end = 16.dp)
        )
        // Estrellas para la calificación
        Row {
            repeat(4) { // Número de estrellas
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = "Estrella",
                    tint = Color.Red,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
