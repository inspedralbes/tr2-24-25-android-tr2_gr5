import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.supportly.model.PeticioResponse
import com.example.supportly.network.RetrofitInstance.api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

@Composable
fun DetailsScreen(peticionId: Int, currentUserId: Int) {
    var peticionDetails: PeticioResponse? by remember { mutableStateOf(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) } // Controla el estado del cuadro de diálogo
    var buttonText by remember { mutableStateOf("Aceptar petición") } // Estado del texto del botón
    var isButtonEnabled by remember { mutableStateOf(true) } // Habilitar o deshabilitar el botón

    LaunchedEffect(key1 = peticionId) {
        try {
            val response = withContext(Dispatchers.IO) {
                api.getPeticionID(peticionId).execute()
            }

            if (response.isSuccessful) {
                peticionDetails = response.body()

                // Verificar si la petición ya tiene un usuario asignado
                peticionDetails?.id_usuari_asignat?.let {
                    if (it != null) {
                        // Si ya está asignada, actualizar el texto del botón
                        buttonText = "Petición ya aceptada"
                        isButtonEnabled = false // Deshabilitar el botón
                    }
                }

            } else {
                error = "No se encontraron detalles para esta petición"
            }
        } catch (e: IOException) {
            error = "Error de red: ${e.message}"
        } catch (e: Exception) {
            error = "Error inesperado: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else if (error != null) {
        Text("Error: $error", modifier = Modifier.padding(16.dp))
    } else {
        peticionDetails?.let { peticion ->
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Nombre: ${peticion.nom_peticio}")
                Text("Descripción: ${peticion.descripcio}")
                Text("Usuario encargado: ${peticion.id_usuari_asignat ?: "Sin asignar"}")

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para aceptar la petición
                Button(
                    onClick = {
                        if (isButtonEnabled) {
                            showDialog = true
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    enabled = isButtonEnabled // El botón está habilitado solo si no está aceptada
                ) {
                    Text(buttonText) // Cambiar el texto del botón según el estado
                }

                // Cuadro de diálogo de confirmación
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Confirmación") },
                        text = { Text("¿Estás seguro de aceptar esta petición?") },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showDialog = false
                                    aceptarPeticion(peticionId, currentUserId)
                                }
                            ) {
                                Text("Acepto")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDialog = false }) {
                                Text("No acepto")
                            }
                        }
                    )
                }
            }
        }
    }
}


// Función para realizar la solicitud PUT
private fun aceptarPeticion(peticionId: Int, currentUserId: Int) {
    val peticionActualizada = PeticioResponse(
        id_peticio = peticionId,
        nom_peticio = "",
        id_usuari = 0,
        descripcio = "",
        id_categoria = 0,
        id_usuari_asignat = currentUserId // Asignar el usuario actual
    )

    api.asignarUsuario(peticionId, peticionActualizada).enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                println("Petición aceptada con éxito")
            } else {
                println("Error al aceptar la petición: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            println("Error de red al aceptar la petición: ${t.message}")
        }
    })
}
