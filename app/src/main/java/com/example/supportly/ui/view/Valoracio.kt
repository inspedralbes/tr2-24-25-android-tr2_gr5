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
    // Valoración para un solo mentor
    var selectedStars by remember { mutableStateOf(0.0) } // 0 estrellas seleccionadas por defecto

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally, // Centra el contenido horizontalmente
        verticalArrangement = Arrangement.Top // Coloca el contenido en la parte superior
    ) {
        // Título en la parte superior
        Text(
            text = "VALORACIÓN DEL MENTOR",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        // Espaciador entre el título y el contenido central
        Spacer(modifier = Modifier.height(1.dp)) // Esto crea el espacio hacia el centro de la pantalla

        // Contenedor para el icono de perfil, estrellas y botón, centrado
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Icono del mentor (centrado y más grande)
            Icon(
                painter = painterResource(id = R.drawable.ic_person), // Reemplaza con tu recurso de ícono
                contentDescription = "Mentor",
                modifier = Modifier.size(120.dp)  // Tamaño grande para el icono
            )

            // Espaciador entre el icono y las estrellas
            Spacer(modifier = Modifier.height(16.dp))

            // Mentor con valoraciones
            MentorRatingItem(
                selectedStars = selectedStars,
                onStarSelected = { starIndex ->
                    selectedStars = starIndex
                }
            )

            // Espaciador entre las estrellas y el botón
            Spacer(modifier = Modifier.height(16.dp))

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
}

@Composable
fun MentorRatingItem(selectedStars: Double, onStarSelected: (Double) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center, // Centra las estrellas horizontalmente
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Estrellas para la calificación
        Row {
            repeat(5) { index -> // 5 estrellas disponibles
                val starValue = index + 1
                val isSelected = selectedStars >= starValue
                val isHalfSelected = selectedStars >= starValue - 0.5 && selectedStars < starValue

                StarIconButton(
                    starIndex = starValue,
                    isSelected = isSelected,
                    isHalfSelected = isHalfSelected,
                    onClick = {


                        onStarSelected(if (isHalfSelected) (starValue - 0.100) else starValue.toDouble())
                    },
                    onDoubleClick = { onStarSelected(starValue.toDouble()) } // Detectamos el doble clic
                )
            }
        }
    }
}

@Composable
fun StarIconButton(starIndex: Int, isSelected: Boolean, isHalfSelected: Boolean, onClick: () -> Unit, onDoubleClick: () -> Unit) {
    var lastClickTime by remember { mutableStateOf(0L) }

    IconButton(
        onClick = {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime < 300) {
                onDoubleClick() // Si el tiempo entre clics es menor que 300 ms, se considera un doble clic
            } else {
                onClick() // Si no es doble clic, es un clic normal
            }
            lastClickTime = currentTime
        },
        modifier = Modifier.padding(4.dp)
    ) {
        val icon = when {
            isSelected -> R.drawable.ic_star
            isHalfSelected -> R.drawable.ic_star_half
            else -> R.drawable.starblack
        }

        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Estrella $starIndex",
            modifier = Modifier.size(32.dp),
            tint = if (isSelected || isHalfSelected) Color.Yellow else Color.Gray
        )
    }
}
