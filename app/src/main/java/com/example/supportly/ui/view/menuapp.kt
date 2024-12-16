package com.example.supportly.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.supportly.ui.theme.DeepNavy
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.*
import com.example.supportly.network.RetrofitInstance
import com.example.supportly.model.PeticioResponse

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
            composable("estadistiques") { MentorRatingsScreen() }
            composable("peticio") { PeticioScreen() }
        }
    }
}

@Composable
fun MenuScreen() {
    val api = RetrofitInstance.api
    var peticioResponse by remember { mutableStateOf(PeticioResponse(emptyList())) }

    LaunchedEffect(Unit) {
        try {
            val response = api.getpeticio().execute()
            if (response.isSuccessful) {
                peticioResponse = response.body() ?: PeticioResponse(emptyList())
            } else {
                println("Error en la respuesta: ${response.errorBody()}")
            }
        } catch (e: Exception) {
            println("Error de red: ${e.message}")
        }
    }

    if (peticioResponse.peticions.isNotEmpty()) {
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
                Text(text = "${peticio.nom_Peticio}: ${peticio.descripcio}")
            }
        }
    } else {
        Text(
            text = "No hay peticiones disponibles.",
            modifier = Modifier.fillMaxSize(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun MentorRatingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Estadísticas y Valoraciones",
            style = MaterialTheme.typography.headlineSmall
        )
        // Aquí añade más contenido o lógica según lo necesites.
    }
}

@Composable
fun PeticioScreen() {
    // Pantalla de Petició
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Gestión de Peticiones",
            style = MaterialTheme.typography.headlineSmall
        )
        // Aquí añade más contenido o lógica según lo necesites.
    }
}
