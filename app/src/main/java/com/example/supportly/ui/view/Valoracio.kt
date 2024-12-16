import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign // Import correcto
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.painter.Painter
import com.example.supportly.R

@Composable
fun MentorRatingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Botón de retroceso
        IconButton(onClick = { /* Acción para retroceder */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = "Volver"
            )
        }

        // Título
        Text(
            text = "VALORACIONES DE MENTOR",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        // Lista de mentores
        Column(modifier = Modifier.fillMaxWidth()) {
            repeat(3) { // Cambia el número de repeticiones según la cantidad de mentores
                MentorRatingItem()
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Espaciador para empujar los botones al final

        // Botones inferiores
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF000080)) // Azul oscuro
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = { /* Acción Foro Principal */ }) {
                Text(text = "Foro Principal", color = Color.White)
            }
            Button(onClick = { /* Acción Estadísticas */ }) {
                Text(text = "Estadísticas, profes con mejor valoración, etc", color = Color.White)
            }
            Button(onClick = { /* Acción Petición */ }) {
                Text(text = "PETICIÓN", color = Color.White)
            }
        }
    }
}

@Composable
fun MentorRatingItem() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Icono del mentor
        Icon(
            painter = painterResource(id = R.drawable.ic_person),
            contentDescription = "Mentor",
            modifier = Modifier
                .size(48.dp)
                .padding(end = 16.dp)
        )
        // Estrellas
        Row {
            repeat(5) { // Número de estrellas
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = "Estrella",
                    tint = Color.Red,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}