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
import com.example.supportly.network.RetrofitInstance.api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    ) {
        NavHost(navController = navController, startDestination = "pantallaInicio") {
            composable("pantallaInicio") { MenuScreen() }
            composable("estadistiques") { /*Estadistiques content*/ }
            composable("peticio"){ }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (selectedItem) {
                0 -> MenuScreen()
                1 -> Text(text = "Estadistiques", style = MaterialTheme.typography.bodyLarge)
                2 -> Text(text = "Petició", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun MenuScreen() {
    var peticioResponse by remember { mutableStateOf(PeticioResponse(emptyList())) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val response = api.getPeticio().execute()
            if (response.isSuccessful) {
                peticioResponse = response.body() ?: PeticioResponse(emptyList())
            } else {
                errorMessage = response.errorBody()?.string()
            }
        } catch (e: Exception) {
            errorMessage = e.message
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else if (!errorMessage.isNullOrEmpty()) {
        Text(text = "Error: $errorMessage", color = Color.Red)
    } else if (peticioResponse.peticions.isNotEmpty()) {
        Text(text = "Petició Detalls:", style = MaterialTheme.typography.bodyLarge)
        peticioResponse.peticions.forEach { peticio ->
            Text(text = "${peticio.nom_peticio}: ${peticio.descripcio}")
        }
    } else {
        Text(text = "No hay peticiones disponibles.")
    }

}








