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
fun ValoracioScreen(onSubmit: () -> Unit = {}) {
    // Lista de mentores con sus estrellas seleccionadas
    val mentors = remember { mutableStateListOf(0, 0, 0, 0, 0, 0) } // 6 mentores, 0 estrellas seleccionadas para cada uno

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
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
            mentors.forEachIndexed { index, selectedStars ->
                MentorRatingItem(
                    selectedStars = selectedStars,
                    onStarSelected = { starIndex ->
                        mentors[index] = starIndex // Actualizar las estrellas para este mentor
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Espaciador flexible

        // Botón de "Enviar"
        Button(
            onClick = { onSubmit() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text(
                text = "Enviar",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
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
        // Icono del mentor (se aumenta el tamaño)
        Icon(
            painter = painterResource(id = R.drawable.ic_person),
            contentDescription = "Mentor",
            modifier = Modifier
                .size(72.dp)  // Tamaño aumentado de 48.dp a 72.dp
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
