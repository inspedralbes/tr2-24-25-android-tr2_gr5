package com.example.supportly.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.supportly.R

@Composable
fun MainPage(
    onNavigateToLogin: () -> Unit,
    onNavigateToSelectResgister: () -> Unit,
) {
    // Fondo azul suave con un toque elegante
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFBFE1FF)) // Fondo azul suave
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Imagen del logo con bordes redondeados y sombra
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo de Supportly",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(200.dp)
                    .padding(bottom = 40.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )

            // Contenedor para los botones con bordes redondeados, sombra y fondo suave
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .fillMaxHeight(0.50f)
                    .background(Color(0xFFF3F8FF)) // Fondo azul claro
                    .padding(vertical = 30.dp, horizontal = 40.dp) // Padding ajustado
                    .clip(RoundedCornerShape(24.dp)) // Bordes más redondeados
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Texto y botón de Registrarse
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "¿Aún no tienes cuenta?",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF004F92) // Azul oscuro para el texto
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))  // Espacio reducido entre el texto y el botón
                        Button(
                            onClick = { onNavigateToSelectResgister() },
                            colors = ButtonDefaults.buttonColors(Color(0xFF007BFF)), // Botón azul vibrante
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .padding(0.dp)
                        ) {
                            Text(
                                text = "Regístrate",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))  // Espacio entre los botones

                    // Texto y botón de Iniciar sesión
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "¿Ya tienes cuenta?",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF004F92) // Azul oscuro para el texto
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))  // Espacio reducido entre el texto y el botón
                        Button(
                            onClick = { onNavigateToLogin() },
                            colors = ButtonDefaults.buttonColors(Color(0xFF007BFF)),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .padding(0.dp)
                        ) {
                            Text(
                                text = "Inicia sesión",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
