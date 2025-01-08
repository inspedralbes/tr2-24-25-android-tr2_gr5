package com.example.supportly.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
                            .height(60.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Logo",
                                modifier = Modifier.size(120.dp)
                            )
                        }
                    }
                },
                backgroundColor = SkyBlue,
                contentColor = MintCream,
                modifier = Modifier.height(100.dp)
            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White,
                contentColor = DeepNavy
            ) {
                val items = listOf("Peticions", "Valoracions", "Perfil")
                val icons = listOf(Icons.Filled.Menu, Icons.Filled.Star, Icons.Filled.AccountCircle)

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
                                2 -> navController.navigate("perfil")
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
            composable("estadistiques") { ValoracioScreen() }
            composable("perfil") { ProfileScreen() }
        }
    }
}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Imagen de perfil
        Image(
            painter = painterResource(id = R.drawable.ic_person),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(120.dp)
                .padding(16.dp)
        )

        // Nombre de usuario
        Text(
            text = "Nombre de Usuario",
            style = MaterialTheme.typography.headlineSmall, // Corregido
            modifier = Modifier.padding(top = 8.dp)
        )

        // Correo electrónico
        Text(
            text = "usuario@gmail.com",
            style = MaterialTheme.typography.bodyMedium, // Corregido
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )

        // Botón para editar perfil
        Button(
            onClick = { /* Navegar a la pantalla de edición */ },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(0.6f)
        ) {
            Text(text = "Editar Perfil")
        }
    }
}

@Composable
fun MenuScreen(navController: NavController) {
    var peticioResponseList: MutableList<PeticioResponse> by remember { mutableStateOf(mutableListOf()) }
    var categoriaList: MutableList<Categoria> by remember { mutableStateOf(mutableListOf()) }
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

            peticioResponseList.clear()
            peticioResponseList.addAll(peticions)

            categoriaList.clear()
            categoriaList.addAll(categories)

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
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp),
        backgroundColor = if (isSelected) Color.Gray else Color.LightGray,
        elevation = 2.dp
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.nom_peticio, style = MaterialTheme.typography.bodyLarge)
        }
    }
}