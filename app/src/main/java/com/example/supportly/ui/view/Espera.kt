package com.example.supportly.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.supportly.network.MyWebSocketListener
import okhttp3.OkHttpClient
import okhttp3.Request

@Composable
fun EsperaScreen(navController: NavController) {
    val context = LocalContext.current
    val client = remember { OkHttpClient() }
    var navigateToMenuApp by remember { mutableStateOf(false) }
    var navigateToLogin by remember { mutableStateOf(false) }
    var rejectionMessage by remember { mutableStateOf("") }

    val wsUrl = "http://10.0.2.2:3000/"

    // WebSocket Listener
    val webSocketListener = remember {
        MyWebSocketListener { mentorId, validado, message ->
            if (validado as Boolean) {
                navigateToMenuApp = true
            } else {
                rejectionMessage = message.toString()
                navigateToLogin = true
            }
        }
    }

    LaunchedEffect(Unit) {
        val request = Request.Builder()
            .url(wsUrl)
            .build()

        client.newWebSocket(request, webSocketListener)
    }

    if (navigateToMenuApp) {
        navController.navigate("menuapp/{email}/{password}") {
            popUpTo("espera") { inclusive = true }
        }
    }

    if (navigateToLogin) {
        Toast.makeText(context, "El tutor ha denegat la teva solicitud", Toast.LENGTH_LONG).show()
        navController.navigate("login") {
            popUpTo("espera") { inclusive = true }
        }
    }

    // Pantalla de espera
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(80.dp),
                color = MaterialTheme.colors.primary,
                strokeWidth = 6.dp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Espera mentres el tutor accepta la solicitud",
                style = MaterialTheme.typography.h6,
                color = Color.Gray
            )
        }
    }
}
