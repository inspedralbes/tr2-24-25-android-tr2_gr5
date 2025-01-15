package com.example.supportly.ui.view.Navegation

import DetailsScreen
import android.util.Log
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
import androidx.compose.material.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.supportly.model.Usuari
import com.example.supportly.ui.view.ChatsScreen
import com.example.supportly.ui.view.EsperaScreen
import com.example.supportly.ui.view.UserChatScreen

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

        composable("detalles/{id_peticio}") { backStackEntry ->
            val idPeticio = backStackEntry.arguments?.getString("id_peticio")?.toIntOrNull()
            val currentUserId = 2;
            if (idPeticio != null) {
                DetailsScreen(
                    peticionId = idPeticio,
                    currentUserId = currentUserId
                ) // Llamar a DetailsScreen con el id_peticio
            } else {
                Text("Petición no encontrada")
            }
        }
        composable("chats_screen") {
            ChatsScreen(sender = "user", navController = navController)
        }
        composable(
            route = "userchat/{correu_alumne}/{nom}",
            arguments = listOf(
                navArgument("correu_alumne") { type = NavType.StringType },
                navArgument("nom") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val correuAlumne = backStackEntry.arguments?.getString("correu_alumne")
            val nom = backStackEntry.arguments?.getString("nom")

            UserChatScreen(correuAlumne = correuAlumne, nom = nom)
        }
    }
}

