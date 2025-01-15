// Menuapp.kt
package com.example.supportly.ui.view

import DetailsScreen
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.supportly.R
import com.example.supportly.model.Categoria
import com.example.supportly.model.PeticioResponse
import com.example.supportly.model.Usuari
import com.example.supportly.network.RetrofitInstance.api
import com.example.supportly.ui.theme.AquaMist
import com.example.supportly.ui.theme.DeepNavy
import com.example.supportly.ui.theme.MintCream
import com.example.supportly.ui.theme.SkyBlue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Menuapp(email: String, password: String) {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }
    val sender = "usuarioActual"
    val currentBackStackEntry = navController.currentBackStackEntryAsState()

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
                                modifier = Modifier
                                    .size(150.dp)
                                    .padding(vertical = 8.dp)
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {

                        navController.navigate("chatscreen/${email}/${password}")
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.Chat, contentDescription = "Chat Icon")
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
        },
        floatingActionButton = {
            if (currentBackStackEntry.value?.destination?.route != "userchat/{correu_alumne}/{nom}/{email}/{password}") {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("añadirPeticion")
                    },
                    backgroundColor = AquaMist,
                    contentColor = Color.White
                ) {
                    Icon(imageVector = Icons.Filled.Create, contentDescription = "Nuevo")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End // Ubicación del botón flotante
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "pantallaInicio",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("pantallaInicio") { MenuScreen(navController) }
            composable("estadistiques") { ValoracioScreen() }
            composable("perfil") {}
            composable("añadirPeticion") { MakeRequest() } // Define la nueva pantalla aquí
            composable(
                "chatscreen/{email}/{password}",
                arguments = listOf(
                    navArgument("email") { type = NavType.StringType },
                    navArgument("password") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val email = backStackEntry.arguments?.getString("email") ?: ""
                val password = backStackEntry.arguments?.getString("password") ?: ""
                ChatsScreen(sender = "user", navController = navController, email = email, password = password)
            }
            composable(
                route = "userchat/{correu_alumne}/{nom}/{email}/{password}",
                arguments = listOf(
                    navArgument("correu_alumne") { type = NavType.StringType },
                    navArgument("nom") { type = NavType.StringType },
                    navArgument("email") { type = NavType.StringType },
                    navArgument("password") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val correuAlumne = backStackEntry.arguments?.getString("correu_alumne")
                val nom = backStackEntry.arguments?.getString("nom")
                val email = backStackEntry.arguments?.getString("email") ?: ""
                val password = backStackEntry.arguments?.getString("password") ?: ""

                UserChatScreen(correuAlumne = correuAlumne, nom = nom, email = email, password = password)
            }
            composable("detalles/{id_peticio}") { backStackEntry ->
                val idPeticio = backStackEntry.arguments?.getString("id_peticio")?.toIntOrNull()
                val currentUserId = 2;
                if (idPeticio != null) {
                    DetailsScreen(peticionId = idPeticio, currentUserId = currentUserId) // Pasar el ID del usuario actual
                } else {

                    Text("Petición no encontrada")
                }
            }
        }
    }
}

@Composable
fun ChatsScreen(sender: String, navController: NavController, email: String, password: String) {
    var messageText by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var users by remember { mutableStateOf<List<Usuari>>(emptyList()) }
    println(email)
    LaunchedEffect(Unit) {
        api.usuaris().enqueue(object : Callback<List<Usuari>> {
            override fun onResponse(
                call: Call<List<Usuari>>,
                response: Response<List<Usuari>>
            ) {
                if (response.isSuccessful) {
                    val filteredUsers = response.body()?.filter {
                        it.correu_alumne != null && it.correu_alumne.isNotEmpty()
                    } ?: emptyList()
                    users = filteredUsers
                }
            }

            override fun onFailure(call: Call<List<Usuari>>, t: Throwable) {
                println("Error en la llamada: ${t.message}")
            }
        })
    }

    // Filtrar usuarios
    val filteredUsers = users.filter {
        it.correu_alumne.contains(searchQuery, ignoreCase = true)
    }

    // UI principal de la pantalla "ChatsScreen"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        androidx.compose.material.Text(
            text = "Buscar usuari per nom",
            style = androidx.compose.material.MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                singleLine = true
            )
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredUsers) { user ->
                UserItemWithIcon(user = user, navController = navController, email, password)
            }
        }
    }
}

@Composable
fun UserItemWithIcon(user: Usuari, navController: NavController, email: String, password: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, androidx.compose.material.MaterialTheme.colors.onSurface)
            .padding(16.dp)
            .clickable {
                navController.navigate("userchat/${user.correu_alumne}/${user.nom}/${email}/${password}")
            }
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            androidx.compose.material.Text(text = "Nom: ${user.nom} ${user.cognom}")
            androidx.compose.material.Text(text = "Correu Alumne: ${user.correu_alumne}")
        }
    }
}


@Composable
fun MenuScreen(navController: NavController) {
    var peticioResponseList: MutableList<PeticioResponse> by remember { mutableStateOf(mutableListOf()) }
    var categoriaList: MutableList<Categoria> by remember { mutableStateOf(mutableListOf()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var selectedCategory by remember { mutableStateOf<Int?>(null) } // Almacena el id_categoria seleccionado
    var searchQuery by remember { mutableStateOf("") }

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
            // Barra de búsqueda
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar por nombre") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            // Mostrar filtro dinámico basado en las categorías
            CategoryFilter(
                categories = categoriaList,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )

            // Filtrar lista según la categoría y el texto de búsqueda
            val filteredList = peticioResponseList.filter { peticio ->
                (selectedCategory == null || peticio.id_categoria == selectedCategory) &&
                        (searchQuery.isEmpty() || peticio.nom_peticio.contains(searchQuery, ignoreCase = true))
            }

            // Mostrar la lista filtrada
            LazyColumn {
                items(filteredList) { item ->
                    MenuItem(item) {
                        navController.navigate("detalles/${item.id_peticio}") // Usar el id_peticio
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
