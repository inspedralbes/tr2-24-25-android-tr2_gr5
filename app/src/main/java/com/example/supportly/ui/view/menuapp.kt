package com.example.supportly.ui.view
import android.telecom.Call
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.supportly.ui.theme.DeepNavy
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.android.car.ui.toolbar.MenuItem
import com.example.supportly.model.PeticioResponse
import com.example.supportly.network.RetrofitInstance
import com.example.supportly.network.RetrofitInstance.api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
            composable("pantallaInicio") { MenuScreen(navController) }
            composable("estadistiques") {}
            composable("peticio") {}
        }
    }
}

@Composable
fun MenuScreen(navController: NavController) {
    var peticioResponseList: MutableList<PeticioResponse> by remember { mutableStateOf(mutableListOf()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = Unit) {
        try {
            val response: List<PeticioResponse> = withContext(Dispatchers.IO) {
                api.peticion().execute().body() ?: emptyList()
            }
            peticioResponseList.clear()
            peticioResponseList.addAll(response)
            isLoading = false
        } catch (e: IOException) {
            error = "Error de red: ${e.message}"
            isLoading = false
        } catch (e: Exception) {
            error = "Error inesperado: ${e.message}"
            isLoading = false
        }
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else if (error != null) {
        Text("Error: $error")
    }
    else {
        // Mostrar la lista de elementos
        LazyColumn {
            items(peticioResponseList) { item ->
                MenuItem(item) {
                    // Acción al hacer clic en un elemento
                    navController.navigate("detalles/${item.nom_peticio}")
                }
            }
        }
    }
}
@Composable
fun MenuItem(item: PeticioResponse, onClick: () -> Unit) {
    // Diseño del elemento de la lista
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.nom_peticio, style = MaterialTheme.typography.bodyLarge)
            Text(text = item.descripcio, style = MaterialTheme.typography.bodyLarge)
        }
    }
}


