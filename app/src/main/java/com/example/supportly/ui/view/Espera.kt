package com.example.supportly.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.supportly.model.Usuari
import com.example.supportly.network.RetrofitInstance.api
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

@Composable
fun EsperaScreen(navController: NavController) {
    val context = LocalContext.current
    var showText by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        api.usuaris().enqueue(object : Callback<List<Usuari>> {
            override fun onResponse(call: Call<List<Usuari>>, response: Response<List<Usuari>>) {
                if (response.isSuccessful) {
                    val usuarios = response.body()
                    val usuarioValido = usuarios?.firstOrNull { it.valid_tut_aula }

                    if (usuarioValido != null) {
                        showText = true
                        navController.navigate("menuapp")
                    }
                } else {
                    println("Error: ${response.message()}")
                }
                loading = false
            }

            override fun onFailure(call: Call<List<Usuari>>, t: Throwable) {
                println("Error de red: ${t.message}")
                loading = false
            }
        })
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(80.dp),
                    color = MaterialTheme.colors.primary,
                    strokeWidth = 6.dp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Espera mentres el tutor t'accepta la solicitud",
                    style = MaterialTheme.typography.h6,
                    color = Color.Gray
                )
            } else {
                if (showText) {
                    Text(
                        text = "¡Registro completado!",
                        style = MaterialTheme.typography.h6,
                        color = Color.Green
                    )
                }
            }
        }
    }
}


