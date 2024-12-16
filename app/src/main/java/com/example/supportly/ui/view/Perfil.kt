//package com.example.supportly.ui.view
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.supportly.R
//
//@Composable
//fun ProfileScreen() {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("") },
//                navigationIcon = {
//                    IconButton(onClick = {}) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_back_arrow),
//                            contentDescription = "Volver"
//                        )
//                    }
//                },
//                actions = {
//                    IconButton(onClick = { }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_person),
//                            contentDescription = "Menú"
//                        )
//                    }
//                },
//                backgroundColor = Color.White,
//                elevation = 4.dp
//            )
//
//
//     { // Agrega un bloque lambda aquí
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Top
//        ) {
//            // Imagen del perfil
//            Image(
//                painter = painterResource(id = R.drawable.ic_person), // Reemplaza con tu drawable
//                contentDescription = "Foto de perfil",
//                modifier = Modifier
//                    .size(100.dp)
//                    .padding(8.dp)
//            )
//
//            // Nombre del usuario
//            Text(
//                text = "Nom d'Usuari",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.padding(8.dp)
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Información del usuario
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.Start
//            ) {
//                Text(text = "Contrasenya:", fontSize = 16.sp, fontWeight = FontWeight.Medium)
//                Text(text = "Correu:", fontSize = 16.sp, fontWeight = FontWeight.Medium)
//                Text(text = "Descripció:", fontSize = 16.sp, fontWeight = FontWeight.Medium)
//            }
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            // Botón de historial
//            Button(
//                onClick = { /* Acción para ver historial */ },
//                shape = RoundedCornerShape(8.dp),
//                modifier = Modifier
//                    .fillMaxWidth(0.7f)
//                    .height(48.dp)
//            ) {
//                Text(text = "Historial d'incidencies", fontSize = 16.sp)
//            }
//        }
//    }
//}
