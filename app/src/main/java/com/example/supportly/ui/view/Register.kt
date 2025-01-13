package com.example.supportly.ui.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.supportly.model.Curs
import com.example.supportly.model.Usuari
import com.example.supportly.network.Mentoria
import com.example.supportly.network.RetrofitInstance
import com.example.supportly.network.RetrofitInstance.api
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CursoSelect(api: Mentoria, onCursoSelected: (Int) -> Unit) {
    var cursos by remember { mutableStateOf<List<Curs>>(emptyList()) }
    var expanded by remember { mutableStateOf(false) }
    var selectedCurso by remember { mutableStateOf<Curs?>(null) }

    LaunchedEffect(Unit) {
        api.curs().enqueue(object : Callback<List<Curs>> {
            override fun onResponse(call: Call<List<Curs>>, response: Response<List<Curs>>) {
                if (response.isSuccessful) {
                    cursos = response.body() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<List<Curs>>, t: Throwable) {
                Log.e("CursoSelect", "Error al obtener los cursos: ${t.message}")
            }
        })
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Botón que abre el menú desplegable
        TextField(
            value = selectedCurso?.nom_curs ?: "Selecciona el teu curs",
            onValueChange = {},
            label = { Text("Curs") },
            readOnly = true, // Hacer que el TextField sea solo lectura
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(vertical = 10.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            cursos.forEach { curso ->
                DropdownMenuItem(
                    onClick = {
                        selectedCurso = curso
                        expanded = false
                        onCursoSelected(curso.id_curs)
                    }
                ) {
                    Text(text = curso.nom_curs)
                }
            }
        }
    }
}


fun sendMentorData(
    navController: NavController,
    nom: String,
    cognom: String,
    correu_alumne: String,
    correu_tutor: String, // Corrección: añadido parámetro correu_tutor
    correu_profe: String,
    contrasenya: String,
    id_curs: Int,
    valid_tut_aula: Int,
    tipus: String, // Corrección: añadido parámetro tipus
    likes: Int,
    peticionesAcabadas: Int
) {
    // Crear el objeto del nuevo mentor
    val newMentor = Usuari(
        nom = nom,
        cognom = cognom,
        correu_alumne = correu_alumne,
        correu_tutor = correu_tutor,
        correu_profe = correu_profe,
        contrasenya = contrasenya,
        id_curs = id_curs,
        valid_tut_aula = valid_tut_aula,
        tipus = tipus,
        likes = likes,
        peticionsAcabades = peticionesAcabadas
    )

    // Realizar la solicitud a la API para registrar el mentor
    api.registerMentor(newMentor).enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                Log.d("RegisterMentor", "Registro exitoso")
                navController.navigate("espera") // Navegar a la pantalla de espera
            } else {
                Log.e("RegisterMentor", "Error en el registro: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("RegisterMentor", "Fallo en la conexión: ${t.message}")
        }
    })
}


@Composable
fun RegisterMentor(navController: NavController) {
    var nom by remember { mutableStateOf("") }
    var cognom by remember { mutableStateOf("") }
    var correu_alumne by remember { mutableStateOf("") }
    var correu_profe by remember { mutableStateOf("") }
    var correu_tutor by remember { mutableStateOf("") }  // Agregado el campo para el correo del tutor
    var contrasenya by remember { mutableStateOf("") }
    var contrasenyaVisible by remember { mutableStateOf(false) }
    var id_curs by remember { mutableStateOf(0) }
    var tipus by remember { mutableStateOf("ment") } // Tipo de usuario
    var likes by remember { mutableStateOf(0) }
    var peticionesAcabadas by remember { mutableStateOf(0) }

    // Menú desplegable para seleccionar el tipo de usuario
    var expandedTipus by remember { mutableStateOf(false) }
    val tipusList = listOf("ment", "alum", "prof")

    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(vertical = 100.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registro Mentor",
            style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Light),
            modifier = Modifier.padding(bottom = 30.dp)
        )
        Text(
            text = "¿Preparado para Ayudar?",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Thin),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Campos de texto
        TextField(
            value = nom,
            onValueChange = { nom = it },
            label = { Text("Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )
        TextField(
            value = cognom,
            onValueChange = { cognom = it },
            label = { Text("Apellido") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )
        TextField(
            value = correu_alumne,
            onValueChange = { correu_alumne = it },
            label = { Text("Correo Alumno") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )
        TextField(
            value = correu_profe,
            onValueChange = { correu_profe = it },
            label = { Text("Correo Profesor") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )
        TextField(
            value = correu_tutor,  // Agregado el campo para el correo del tutor
            onValueChange = { correu_tutor = it },
            label = { Text("Correo Tutor") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )
        TextField(
            value = contrasenya,
            onValueChange = { contrasenya = it },
            label = { Text("Contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            visualTransformation = if (contrasenyaVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (contrasenyaVisible) "🙈" else "👁️"
                IconButton(onClick = { contrasenyaVisible = !contrasenyaVisible }) {
                    Text(image)
                }
            }
        )

        // Aquí integramos el selector de cursos
        CursoSelect(api) { selectedId ->
            id_curs = selectedId as Int // Asignamos el ID del curso seleccionado
        }

        // Menú desplegable para seleccionar el tipo de usuario
        TextField(
            value = tipus,
            onValueChange = {},
            label = { Text("Tipo de Usuario") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedTipus = !expandedTipus }
                .padding(vertical = 10.dp)
        )

        DropdownMenu(
            expanded = expandedTipus,
            onDismissRequest = { expandedTipus = false }
        ) {
            tipusList.forEach { type ->
                DropdownMenuItem(
                    onClick = {
                        tipus = type
                        expandedTipus = false
                    }
                ) {
                    Text(text = type.capitalize())
                }
            }
        }

        Button(
            onClick = {
                if (id_curs != 0 && nom.isNotEmpty() && cognom.isNotEmpty() &&
                    correu_alumne.isNotEmpty() && correu_profe.isNotEmpty() &&
                    correu_tutor.isNotEmpty() && contrasenya.isNotEmpty()) {
                    sendMentorData(
                        navController,
                        nom,
                        cognom,
                        correu_alumne,
                        correu_profe,
                        correu_tutor,  // Ahora se envía el correo del tutor
                        contrasenya,
                        id_curs,
                        valid_tut_aula = Int.MAX_VALUE,  // Puedes ajustar este valor según lo necesario
                        tipus = tipus ,// Se envía el tipo seleccionado
                        likes = likes,
                        peticionesAcabadas = peticionesAcabadas
                    )
                    navController.navigate("espera")
                } else {
                    Log.e("RegisterMentor", "Faltan datos o ID Curso inválido")
                }
            },
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text("Registrar Mentor")
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