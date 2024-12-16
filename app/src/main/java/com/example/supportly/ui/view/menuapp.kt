package com.example.supportly.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import com.example.supportly.R
import com.example.supportly.network.RetrofitInstance
import com.example.supportly.model.PeticioResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menuapp() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }

    val navigationItems = listOf(
        NavigationItem("Menú", Icons.Filled.Home, "pantallaInicio"),
        NavigationItem("Petició", Icons.Filled.Create, "peticio"),
        NavigationItem("Valoració", Icons.Filled.Star, "valoracio")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Menú Principal", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue
                )
            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White,
                contentColor = Color.Blue
            ) {
                navigationItems.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item.route)
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
            composable("valoracio") { ValoracioScreen() } // Llama a ValoracioScreen
        }
    }
}

@Composable
fun MenuScreen() {
    val api = RetrofitInstance.api
    var peticioResponse by remember { mutableStateOf(PeticioResponse(emptyList())) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            val response = api.getpeticio().execute()
            if (response.isSuccessful) {
                peticioResponse = response.body() ?: PeticioResponse(emptyList())
            } else {
                errorMessage = "Error en la respuesta: ${response.errorBody()}"
            }
        } catch (e: Exception) {
            errorMessage = "Error de red: ${e.message}"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red, style = MaterialTheme.typography.bodyLarge)
        } else if (peticioResponse.peticions.isNotEmpty()) {
            Text(
                text = "Petició Detalls:",
                style = MaterialTheme.typography.headlineSmall
            )
            peticioResponse.peticions.forEach { peticio ->
                Text(text = "${peticio.nom_Peticio}: ${peticio.descripcio}")
            }
        } else {
            Text(
                text = "No hay peticiones disponibles.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
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
    }
}

@Composable
fun PeticioScreen() {
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
    }
}

data class NavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)
