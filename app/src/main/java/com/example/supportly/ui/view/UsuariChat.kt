package com.example.supportly.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.supportly.model.Usuari
import java.lang.reflect.Modifier

@Composable
fun UsuariChat(user: Usuari, navController: NavController) {
    Column{
        Text(text = "Nom: ${user.nom} ${user.cognom}")
        Text(text = "Correu Alumne: ${user.correu_alumne}")
    }
}

