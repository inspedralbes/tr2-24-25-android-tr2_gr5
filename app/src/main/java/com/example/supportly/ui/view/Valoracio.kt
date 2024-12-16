package com.example.supportly.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    var selectedStars by remember { mutableStateOf(0) } // Estado para las estrellas seleccionadas

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
                MentorRatingItem(selectedStars = selectedStars, onStarSelected = { starIndex ->
                    selectedStars = starIndex
                })
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Espaciador flexible
    }
}

@Composable
fun MentorRatingItem(selectedStars: Int, onStarSelected: (Int) -> Unit) {
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
            repeat(5) { index -> // 5 estrellas disponibles
                StarIconButton(
                    starIndex = index + 1,
                    isSelected = selectedStars >= index + 1,
                    onClick = { onStarSelected(index + 1) }
                )
            }
        }
    }
}

@Composable
fun StarIconButton(starIndex: Int, isSelected: Boolean, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.padding(4.dp)
    ) {
        Icon(
            painter = painterResource(
                id = if (isSelected) R.drawable.ic_star else R.drawable.starblack
            ),
            contentDescription = "Estrella $starIndex",
            modifier = Modifier.size(32.dp),
            tint = if (isSelected) Color.Yellow else Color.Gray
        )
    }
}
