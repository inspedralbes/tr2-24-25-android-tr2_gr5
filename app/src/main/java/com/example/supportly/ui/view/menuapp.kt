package com.example.supportly.ui.view
import android.telecom.Call
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.supportly.ui.theme.DeepNavy
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.*
import com.example.supportly.model.PeticioResponse
import com.example.supportly.network.RetrofitInstance
import okio.IOException

@Composable
fun Menuapp() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Menú Principal") },
                backgroundColor = Color.Blue,
                contentColor = Color.White,
                modifier = Modifier.height(56.dp)
            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White,
                contentColor = DeepNavy
            ) {
                val items = listOf("Menú", "Estadistiques", "Petició")
                val icons = listOf(Icons.Filled.Home, Icons.Filled.Star, Icons.Filled.Create)

                items.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = icons[index],
                                contentDescription = item
                            )
                        },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            when (index) {
                                0 -> navController.navigate("pantallaInicio")
                                1 -> navController.navigate("estadistiques")
                                2 -> navController.navigate("peticio")
                            }
                        },
                        selectedContentColor = Color.Blue,
                        unselectedContentColor = Color.Gray
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "pantallaInicio",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("pantallaInicio") { MenuScreen() }
            composable("estadistiques") {}
            composable("peticio") {}
        }
    }
}

@Composable
fun MenuScreen() {
    val api = RetrofitInstance.api
    var peticioResponse by remember { mutableStateOf(PeticioResponse(emptyList())) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val response = api.peticion().execute()
            if (response.isSuccessful) {
                println("hola")
                response.body()?.let {
                    peticioResponse = it
                } ?: run {
                    errorMessage = "La respuesta está vacía o no contiene peticiones."
                }
            } else {
                errorMessage = "Error en la respuesta: ${response.errorBody()?.string()}"
            }
        } catch (e: IOException) {
            errorMessage = "Error de red: ${e.message}"
        } catch (e: Exception) {
            errorMessage = "Ocurrió un error inesperado: ${e.message}"
        }
    }

    if (errorMessage != null) {
        Text(
            text = errorMessage!!,
            modifier = Modifier.fillMaxSize(),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Red
        )
    } else if (peticioResponse.peticions.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Petició Detalls:",
                style = MaterialTheme.typography.headlineSmall
            )
            peticioResponse.peticions.forEach { peticio ->
                Text(text = "${peticio.nom_peticio}: ${peticio.descripcio}")
            }
        }
    } else {
        Text(
            text = "No hay peticiones disponibles.",
            modifier = Modifier.fillMaxSize(),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}



