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
import com.example.supportly.network.RetrofitInstance
import com.example.supportly.model.PeticioResponse
import okhttp3.Response
import retrofit2.Callback

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
            composable("pantallaInicio") { /*MenuScreen()*/ }
            composable("estadistiques") { /*Estadistiques content*/ }
            composable("peticio"){ }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it) // Aquí es donde se usa 'it', que es 'innerPadding'
                .padding(top = 16.dp), // Asegura espacio arriba de la pantalla
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (selectedItem) {
                /*0 -> MenuScreen()*/
                1 -> Text(text = "Estadistiques", style = MaterialTheme.typography.bodyLarge)
                2 -> Text(text = "Petició", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
/*
@Composable
fun MenuScreen() {
    val api = RetrofitInstance.api
    var peticioResponse by remember { mutableStateOf(PeticioResponse(emptyList())) }

    LaunchedEffect(Unit) {
        try {
            api.getpeticio().enqueue(object : Callback<PeticioResponse> {
                override fun onResponse(call: Call<PeticioResponse>, response: Response<PeticioResponse>) {
                    if (response.isSuccessful) {
                        peticioResponse = response.body() ?: PeticioResponse(emptyList())
                    } else {
                        Log.e("MenuScreen", "Error en la respuesta: ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<PeticioResponse>, t: Throwable) {
                    Log.e("MenuScreen", "Error de red: ${t.message}")
                }
            })
        } catch (e: Exception) {
            Log.e("MenuScreen", "Error de red: ${e.message}")
        }
    }

    if (peticioResponse.peticions.isNotEmpty()) {
        Text(text = "Petició Detalls:", style = MaterialTheme.typography.bodyLarge)
        peticioResponse.peticions.forEach { peticion ->
            Text(text = "${peticion.nom_Peticio}: ${peticion.descripcio}")
        }
    } else {
        Text(text = "No hay peticiones disponibles.")
    }
}
*/


