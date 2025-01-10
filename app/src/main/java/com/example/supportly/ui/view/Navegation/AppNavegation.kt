package com.example.supportly.ui.view.Navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.supportly.ui.view.Login
import com.example.supportly.ui.view.MainPage
import com.example.supportly.ui.view.RegisterAlumne
import com.example.supportly.ui.view.RegisterMentor
import com.example.supportly.ui.view.TipusRegister
import com.example.supportly.ui.view.Menuapp
import com.example.supportly.ui.view.ValoracioScreen
import com.example.supportly.ui.view.DetailsScreen // Importar la pantalla de detalles
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text

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

        composable("menuapp") {
            Menuapp()
        }

        // Añadir la ruta para la pantalla de Valoracio
        composable("estadistiques") {
            ValoracioScreen() // Redirige a la pantalla de Valoracio
        }

        // Nueva ruta para detalles de una petición
        composable("detalles/{id_peticio}") { backStackEntry ->
            val idPeticio = backStackEntry.arguments?.getString("id_peticio")?.toIntOrNull()
            if (idPeticio != null) {
                DetailsScreen(peticionId = idPeticio) // Llamar a DetailsScreen con el id_peticio
            } else {
                // En caso de que el id no sea válido, muestra un mensaje de error
                Text("Petición no encontrada")
            }
        }
    }
}
