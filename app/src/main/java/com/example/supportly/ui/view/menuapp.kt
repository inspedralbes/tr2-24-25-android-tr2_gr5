package com.example.supportly.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.supportly.R
import com.example.supportly.model.Categoria
import com.example.supportly.model.PeticioResponse
import com.example.supportly.network.RetrofitInstance.api
import com.example.supportly.ui.theme.DeepNavy
import com.example.supportly.ui.theme.MintCream
import com.example.supportly.ui.theme.SkyBlue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menuapp() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(120.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = SkyBlue,
                    titleContentColor = MintCream
                ),
                modifier = Modifier.height(100.dp)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = DeepNavy
            ) {
                val items = listOf("Peticions", "Valoracions", "Perfil")
                val icons = listOf(Icons.Filled.Menu, Icons.Filled.Star, Icons.Filled.AccountCircle)

                items.forEachIndexed { index, item ->
                    NavigationBarItem(
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
                                2 -> navController.navigate("perfil")
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Blue,
                            unselectedIconColor = Color.Gray,
                            indicatorColor = Color.LightGray
                        )
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
            composable("estadistiques") { ValoracioScreen() }
            composable("perfil") { ProfileScreen(navController) }
            composable("editarPerfil") { EditProfileScreen(navController) }
        }
    }
}

@Composable
fun ProfileScreen(navController: NavController) {
    var selectedImage by rememberSaveable { mutableStateOf(R.drawable.futbol) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = selectedImage),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(120.dp)
                .padding(16.dp)
        )

        Text(
            text = "Nombre de Usuario",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = "usuario@gmail.com",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )

        Button(
            onClick = {
                navController.navigate("editarPerfil")
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(0.6f)
        ) {
            Text(text = "Editar Perfil")
        }

        Text("Selecciona una foto de perfil", modifier = Modifier.padding(top = 16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                ImageSelection(
                    imageRes = R.drawable.futbol,
                    isSelected = selectedImage == R.drawable.futbol,
                    onClick = { selectedImage = R.drawable.futbol }
                )
            }
            item {
                ImageSelection(
                    imageRes = R.drawable.finlandia,
                    isSelected = selectedImage == R.drawable.finlandia,
                    onClick = { selectedImage = R.drawable.finlandia }
                )
            }
        }
    }
}

@Composable
fun EditProfileScreen(navController: NavController) {
    var selectedImage by rememberSaveable { mutableStateOf(R.drawable.futbol) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = selectedImage),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(120.dp)
                .padding(16.dp)
        )

        Text(
            text = "Editar Foto de Perfil",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                ImageSelection(
                    imageRes = R.drawable.futbol,
                    isSelected = selectedImage == R.drawable.futbol,
                    onClick = { selectedImage = R.drawable.futbol }
                )
            }
            item {
                ImageSelection(
                    imageRes = R.drawable.finlandia,
                    isSelected = selectedImage == R.drawable.finlandia,
                    onClick = { selectedImage = R.drawable.finlandia }
                )
            }
        }

        Button(
            onClick = {
                navController.navigateUp()  // Vuelve al perfil
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(0.6f)
        ) {
            Text(text = "Guardar Cambios")
        }
    }
}

@Composable
fun ImageSelection(imageRes: Int, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.Gray else Color.LightGray
        ),
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Imagen seleccionada",
            modifier = Modifier.size(80.dp)
        )
    }
}

@Composable
fun MenuScreen(navController: NavController) {
    var peticioResponseList by remember { mutableStateOf<List<PeticioResponse>>(emptyList()) }
    var categoriaList by remember { mutableStateOf<List<Categoria>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var selectedCategory by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(key1 = Unit) {
        try {
            val peticions: List<PeticioResponse> = withContext(Dispatchers.IO) {
                api.peticion().execute().body() ?: emptyList()
            }
            val categories: List<Categoria> = withContext(Dispatchers.IO) {
                api.categoria().execute().body() ?: emptyList()
            }

            peticioResponseList = peticions
            categoriaList = categories

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
    } else {
        Column {
            CategoryFilter(
                categories = categoriaList,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )

            val filteredList = if (selectedCategory == null) {
                peticioResponseList
            } else {
                peticioResponseList.filter { it.id_categoria == selectedCategory }
            }

            LazyColumn {
                items(filteredList) { item ->
                    MenuItem(item) {
                        navController.navigate("detalles/${item.nom_peticio}")
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryFilter(
    categories: List<Categoria>,
    selectedCategory: Int?,
    onCategorySelected: (Int?) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                category = "Todas",
                isSelected = selectedCategory == null,
                onClick = { onCategorySelected(null) }
            )
        }
        items(categories) { category ->
            FilterChip(
                category = category.nom,
                isSelected = category.id_categoria == selectedCategory,
                onClick = { onCategorySelected(category.id_categoria) }
            )
        }
    }
}

@Composable
fun FilterChip(category: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.Gray else Color.LightGray
        ),
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = category,
            modifier = Modifier.padding(8.dp),
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
fun MenuItem(item: PeticioResponse, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.nom_peticio, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
