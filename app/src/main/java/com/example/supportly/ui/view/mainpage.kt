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
    // Asegurando que el fondo azul ocupe toda la pantalla
    Box(
        modifier = Modifier
            .fillMaxSize()  // Asegura que el fondo azul ocupe toda la pantalla
            .background(Color(0xFF2699E6)) // Fondo azul claro general
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()  // Asegura que la columna ocupe toda la pantalla
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Imagen del logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo de Supportly",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(180.dp)
                    .padding(bottom = 40.dp),
                contentScale = ContentScale.Fit
            )

            // Contenedor de los botones con fondo más claro
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f) // Ajustamos el ancho del contenedor
                    .background(Color(0xFFB3D4FF)) // Fondo más claro para la caja interna
                    .padding(vertical = 12.dp, horizontal = 20.dp) // Padding dentro de la caja
                    .clip(RoundedCornerShape(16.dp)) // Bordes redondeados
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Botón Registrarse
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "¿Aún no tienes cuenta?",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { onNavigateToSelectResgister() },
                            colors = ButtonDefaults.buttonColors(Color(0xFF1A66CC)), // Fondo de los botones con azul fuerte
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .padding(0.dp)  // Aseguramos que no haya padding extra en los botones
                        ) {
                            Text(
                                text = "Regístrate",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Botón Iniciar sesión
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "¿Ya tienes cuenta?",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { onNavigateToLogin() },
                            colors = ButtonDefaults.buttonColors(Color(0xFF1A66CC)), // Fondo de los botones con azul fuerte
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .padding(0.dp)  // Aseguramos que no haya padding extra en los botones
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
