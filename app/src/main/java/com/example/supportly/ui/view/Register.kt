package com.example.supportly.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

//FORMULARIO DE REGISTRO DE MENTOR
@Composable
fun RegisterMentor(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(vertical = 100.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "Registro Mentor",
            style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Light),
            modifier = Modifier
                .padding(bottom = 30.dp)

        )
        Text(
            text = "¿Preparado para Ayudar?",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Thin),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Lista de campos de texto
        var nom by remember { mutableStateOf("") }
        var emailAlumne by remember { mutableStateOf("") }
        var emailProfesor by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var numTlf by remember { mutableStateOf("") }

        listOf(
            "Nombre" to nom,
            "Correo Electrónico" to emailAlumne,
            "Correo Electrónico Tutor Instituto" to emailProfesor,
            "Número Teléfono" to numTlf
        ).forEach { (label, value) ->
            TextField(
                value = value,
                onValueChange = { newValue ->
                    when (label) {
                        "Nombre" -> nom = newValue
                        "Correo Electrónico" -> emailAlumne = newValue
                        "Correo Electrónico Tutor Instituto" -> emailProfesor = newValue
                        "Número Teléfono" -> numTlf = newValue
                    }
                },
                label = { Text(label) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )
        }

        // Campo de contraseña
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            placeholder = { Text("Escribe tu contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) "🙈" else "👁️"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Text(icon)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de Registro
        Button(
            onClick = { navController.navigate("menuapp")},
            colors = ButtonDefaults.buttonColors(Color.Blue),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp, horizontal = 45.dp)
        ) {
            Text(text = "Registrarse como Mentor")
        }
    }
}



//HACER EL FORMULARIO DE REGISTRO DE ALUMNE
@Composable
fun RegisterAlumne(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(vertical = 100.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "Registro Alumno",
            style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Light),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "¿Preparado para aprender?",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Thin),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Lista de campos de texto
        var nombre by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var emailTutor by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var telefono by remember { mutableStateOf("") }
        var curso by remember { mutableStateOf("") }

        listOf(
            "Nombre" to nombre,
            "Correo Electrónico" to email,
            "Correo Electrónico Tutor" to emailTutor,
            "Número de Teléfono" to telefono,
            "Curso" to curso
        ).forEach { (label, value) ->
            TextField(
                value = value,
                onValueChange = { newValue ->
                    when (label) {
                        "Nombre" -> nombre = newValue
                        "Correo Electrónico" -> email = newValue
                        "Correo Electrónico Tutor" -> emailTutor = newValue
                        "Número de Teléfono" -> telefono = newValue
                        "Curso" -> curso = newValue
                    }
                },
                label = { Text(label) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )
        }

        // Campo de contraseña
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            placeholder = { Text("Escribe tu contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) "🙈" else "👁️"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Text(icon)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de Registro
        Button(
            onClick = { navController.navigate("menuapp") },
            colors = ButtonDefaults.buttonColors(Color.Blue),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp, horizontal = 45.dp)
        ) {
            Text(text = "Registrarse como Alumno")
        }
    }
}
