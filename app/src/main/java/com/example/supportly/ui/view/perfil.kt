package com.example.supportly.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.supportly.R


@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_person),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(120.dp) // Tamaño igual para todas las imágenes
                .clip(CircleShape) // Forma circular
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
    }
}

@Composable
fun EditProfileScreen(navController: NavController) {
    var selectedImage by rememberSaveable { mutableStateOf(R.drawable.ic_person) }

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
                .size(120.dp) // Tamaño igual para todas las imágenes
                .clip(CircleShape) // Forma circular
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
                    imageRes = R.drawable.bulbasour,
                    isSelected = selectedImage == R.drawable.bulbasour,
                    onClick = { selectedImage = R.drawable.bulbasour }
                )
            }

            item {
                ImageSelection(
                    imageRes = R.drawable.charmader,
                    isSelected = selectedImage == R.drawable.charmader,
                    onClick = { selectedImage = R.drawable.charmader }
                )
            }

            item {
                ImageSelection(
                    imageRes = R.drawable.squirtel,
                    isSelected = selectedImage == R.drawable.squirtel,
                    onClick = { selectedImage = R.drawable.squirtel }
                )
            }

            item {
                ImageSelection(
                    imageRes = R.drawable.unnamed,
                    isSelected = selectedImage == R.drawable.unnamed,
                    onClick = { selectedImage = R.drawable.unnamed }
                )
            }

            item {
                ImageSelection(
                    imageRes = R.drawable.volleyball,
                    isSelected = selectedImage == R.drawable.volleyball,
                    onClick = { selectedImage = R.drawable.volleyball }
                )
            }

            item {
                ImageSelection(
                    imageRes = R.drawable.futbol,
                    isSelected = selectedImage == R.drawable.futbol,
                    onClick = { selectedImage = R.drawable.futbol }
                )
            }

            item {
                ImageSelection(
                    imageRes = R.drawable.futbolamericano,
                    isSelected = selectedImage == R.drawable.futbolamericano,
                    onClick = { selectedImage = R.drawable.futbolamericano }
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
            modifier = Modifier
                .size(80.dp) // Tamaño igual para todas las imágenes
                .clip(CircleShape) // Forma circular
        )
    }
}