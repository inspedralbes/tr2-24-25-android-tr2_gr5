package com.example.supportly.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TipusRegister(
    onNavigateToResgister: () -> Unit,
){

    val opciones = listOf("Mentor", "Alumne")
    var opcionSeleccionada by remember { mutableStateOf(opciones[0]) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(100.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier
                    .padding(50.dp),
                text = "Elige el tipo de registro:",
                style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Light)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            opciones.forEach { opcion ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    // RadioButton
                    androidx.compose.material3.RadioButton(
                        selected = opcion == opcionSeleccionada,
                        onClick = { opcionSeleccionada = opcion }
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    // Texto de la opción
                    Text(
                        text = opcion,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
        }

            //ATENCIIÓN!!!!!!!

            // HACER QUE SI LA SELECCION DE OPCION ES ALUMNO, SE VAYA AL REGISTRO DE ALUMNOS
            // O QUE SI LA SELECCIONES PROFESOR SE VAYA AL REGISTRO DE PROFESOR


        Spacer(modifier = Modifier.height(50.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {onNavigateToResgister()},
                colors = ButtonDefaults.buttonColors(Color.Blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Continuar Registre")
            }
        }
    }
}
}



