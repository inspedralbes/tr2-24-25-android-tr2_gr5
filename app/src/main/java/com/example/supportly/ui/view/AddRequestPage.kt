package com.example.supportly.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.supportly.model.PeticioResponse
import com.example.supportly.network.RetrofitInstance.api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun MakeRequest() {
    var idCategoria by remember { mutableStateOf("") }
    var nomPeticio by remember { mutableStateOf("") }
    var descripcio by remember { mutableStateOf("") }
    var idAssignat by remember { mutableStateOf("") }
    var mensajeResultado by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = idCategoria,
            onValueChange = { idCategoria = it },
            label = { Text("ID Categoría") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = nomPeticio,
            onValueChange = { nomPeticio = it },
            label = { Text("Nombre de la Petición") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = descripcio,
            onValueChange = { descripcio = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = idAssignat,
            onValueChange = { idAssignat = it },
            label = { Text("Usuario asignado a la peticion") },
            modifier = Modifier.fillMaxWidth()
        )


        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    val nuevaPeticion = PeticioResponse(
                        id_peticio = 0,
                        id_usuari_asignat = idAssignat.toIntOrNull() ?: 0,
                        id_usuari = 1, // No se usa ni se envía un valor explícito
                        id_categoria = idCategoria.toIntOrNull() ?: 0,
                        nom_peticio = nomPeticio,
                        descripcio = descripcio

                    )

                    api.crearPeticion(nuevaPeticion).enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            mensajeResultado = if (response.isSuccessful) {
                                "Petición creada exitosamente"
                            } else {
                                "Error al crear petición: ${response.message()}"
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            mensajeResultado = "Error de red: ${t.message}"
                        }
                    })
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear Petición")
        }

        Spacer(modifier = Modifier.height(16.dp))

        mensajeResultado?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


