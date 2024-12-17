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
import androidx.compose.foundation.layout.*
import com.example.supportly.network.Mentoria
import com.example.supportly.ui.view.Menuapp
import com.example.supportly.ui.view.peticioinfo


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
        /*
        composable("peticioinfo/{id_peticio}") { backStackEntry ->
            val id_peticio = backStackEntry.arguments?.getString("id_peticio")
            peticioinfo(id_peticio)
        }
        */

    }
}




