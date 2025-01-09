package com.example.supportly.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.supportly.model.LoginRequest
import com.example.supportly.model.LoginResponse
import com.example.supportly.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Login(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Obtener el contexto actual para el Toast
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier.padding(50.dp),
            text = "Inicio de Sesión",
            style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Light)
        )

        Spacer(modifier = Modifier.height(60.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            placeholder = { Text("Ingresa tu correo") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            placeholder = { Text("Ingresa tu contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    val icon = if (passwordVisible) "🙈" else "👁️"
                    Text(icon)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val credentials = LoginRequest(email, password)

                RetrofitInstance.api.login(credentials).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            if (loginResponse?.user != null) {
                                navController.navigate("menuapp")
                            } else {
                                Toast.makeText(context, "Error: ${loginResponse?.message}", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(context, "Error en el servidor: ${response.code()} - ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(context, "Error en la conexión: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
            },
            colors = ButtonDefaults.buttonColors(Color.Blue),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 16.dp)
        ) {
            Text(text = "Iniciar sesión")
        }
    }
}
