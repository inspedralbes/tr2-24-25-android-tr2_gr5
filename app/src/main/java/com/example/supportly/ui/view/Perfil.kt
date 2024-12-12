package com.example.supportly.ui.view


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.supportly.R


@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
                navigationIcon = {
                    IconButton(onClick = { /* Acción de retroceder */ }) {
                        Icon(painter = painterResource(id = R.drawable.ic_back_arrow), contentDescription = "Retroceder")
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Icon(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "Icono de usuario",
                    modifier = Modifier.size(100.dp)
                )


                Spacer(modifier = Modifier.height(16.dp))


                // Nombre de usuario
                Text(
                    text = "Nombre de Usuario",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                )


                Spacer(modifier = Modifier.height(24.dp))


                // Campos de información del perfil
                ProfileInputField(label = "Contraseña")
                Spacer(modifier = Modifier.height(8.dp))
                ProfileInputField(label = "Correo Electrónico")
                Spacer(modifier = Modifier.height(8.dp))
                ProfileInputField(label = "Descripción")


                Spacer(modifier = Modifier.height(24.dp))


                // Botón de historial de incidencias
                Button(
                    onClick = { /* Acción al pulsar el botón */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
                ) {
                    Text(text = "Historial de Incidencias", color = Color.White)
                }
            }
        }
    )
}


@Composable
fun ProfileInputField(label: String) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(
            value = "",
            onValueChange = { /* Acción de cambio */ },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}

