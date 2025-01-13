package com.example.supportly.ui.view.Navegation

import DetailsScreen
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
 // Importar la pantalla de detalles
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.supportly.ui.view.ChatViewModel
import com.example.supportly.ui.view.ChatsScreen
import com.example.supportly.ui.view.EsperaScreen

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

        composable("espera") {
            EsperaScreen(navController)
        }

        composable("estadistiques") {
            ValoracioScreen()
        }

        // Nueva ruta para detalles de una petición
        composable("detalles/{id_peticio}") { backStackEntry ->
            val idPeticio = backStackEntry.arguments?.getString("id_peticio")?.toIntOrNull()
            val currentUserId = 2;
            if (idPeticio != null) {
                DetailsScreen(peticionId = idPeticio, currentUserId = currentUserId) // Llamar a DetailsScreen con el id_peticio
            } else {
                Text("Petición no encontrada")
            }
        }
        composable("chatsScreen/{sender}") { backStackEntry ->
            val sender = backStackEntry.arguments?.getString("sender") ?: ""

            val chatViewModel: ChatViewModel = viewModel()
            ChatsScreen(viewModel = chatViewModel, sender = sender)
        }

    }
}
