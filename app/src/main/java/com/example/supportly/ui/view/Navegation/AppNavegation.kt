package com.example.supportly.ui.view.Navegation

import DetailsScreen
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.supportly.ui.view.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "mainPage") {
        composable("mainPage") {
            MainPage(
                onNavigateToLogin = {
                    navController.navigate("login")
                },
                onNavigateToSelectResgister = {
                    navController.navigate("selectRegister")
                }
            )
        }

        composable("login") {
            Login(navController = navController)
        }

        composable("selectRegister") {
            TipusRegister(
                onNavigateToResgister = { userType ->
                    when (userType) {
                        "Mentor" -> navController.navigate("registerMentor")
                        "Alumne" -> navController.navigate("registerAlumne")
                    }
                }
            )
        }

        composable("registerMentor") {
            RegisterMentor(navController = navController)
        }

        composable("registerAlumne") {
            RegisterAlumne(navController = navController)
        }

        // Ruta de 'menuapp' con argumentos
        composable(
            "menuapp/{email}/{password}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""
            Menuapp(email = email, password = password)
        }

        composable("espera") {
            EsperaScreen(navController = navController)
        }

        // Ruta para estadísticas
        composable("estadistiques") {
            ValoracioScreen()
        }

        // Ruta para detalles de una petición
        composable(
            "detalles/{id_peticio}",
            arguments = listOf(
                navArgument("id_peticio") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idPeticio = backStackEntry.arguments?.getInt("id_peticio")
            val currentUserId = 2 // Ejemplo de ID de usuario actual
            if (idPeticio != null) {
                DetailsScreen(peticionId = idPeticio, currentUserId = currentUserId)
            } else {
                // En caso de que el ID no sea válido
                Text("Petición no encontrada")
            }
        }
    }
}
