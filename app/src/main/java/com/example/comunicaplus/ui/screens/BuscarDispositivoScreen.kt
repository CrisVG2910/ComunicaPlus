package com.example.comunicaplus.ui.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comunicaplus.services.DeviceLocationService
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun BuscarDispositivoScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {

    val context =
        LocalContext.current

    val scope =
        rememberCoroutineScope()

    val locationService =
        remember(context) {
            DeviceLocationService(context)
        }

    var latitud by remember {
        mutableStateOf<String?>(null)
    }

    var longitud by remember {
        mutableStateOf<String?>(null)
    }

    var obteniendo by remember {
        mutableStateOf(false)
    }

    var mensajeError by remember {
        mutableStateOf<String?>(null)
    }

    fun obtenerUbicacion() {

        scope.launch {

            obteniendo = true
            mensajeError = null

            val resultado =
                locationService
                    .obtenerUbicacionActual()

            resultado.onSuccess {
                    ubicacion ->

                latitud =
                    String.format(
                        Locale.US,
                        "%.6f",
                        ubicacion.latitude
                    )

                longitud =
                    String.format(
                        Locale.US,
                        "%.6f",
                        ubicacion.longitude
                    )
            }

            resultado.onFailure {
                    error ->

                mensajeError =
                    error.message
                        ?: "No fue posible obtener la ubicación."
            }

            obteniendo = false
        }
    }

    val permisoUbicacionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts
                .RequestMultiplePermissions()
        ) { permisos ->

            val permisoPreciso =
                permisos[
                    Manifest.permission
                        .ACCESS_FINE_LOCATION
                ] == true

            val permisoAproximado =
                permisos[
                    Manifest.permission
                        .ACCESS_COARSE_LOCATION
                ] == true

            if (
                permisoPreciso ||
                permisoAproximado
            ) {

                obtenerUbicacion()

            } else {

                mensajeError =
                    "Debes permitir el acceso a la ubicación para utilizar esta función."
            }
        }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 24.dp,
                vertical = 24.dp
            ),
        verticalArrangement =
            Arrangement.Center,
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text = "Buscar dispositivo",
            fontSize = 30.sp,
            fontWeight =
                FontWeight.Bold,
            color =
                MaterialTheme
                    .colorScheme
                    .primary
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        Text(
            text =
                "Obtén la ubicación actual del dispositivo.",
            style =
                MaterialTheme
                    .typography
                    .bodyLarge
        )

        Spacer(
            modifier =
                Modifier.height(32.dp)
        )

        Button(
            onClick = {

                mensajeError = null

                if (
                    locationService
                        .tienePermisoUbicacion()
                ) {

                    obtenerUbicacion()

                } else {

                    permisoUbicacionLauncher
                        .launch(
                            arrayOf(
                                Manifest.permission
                                    .ACCESS_FINE_LOCATION,

                                Manifest.permission
                                    .ACCESS_COARSE_LOCATION
                            )
                        )
                }
            },
            enabled = !obteniendo,
            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text(
                if (obteniendo) {
                    "Obteniendo ubicación..."
                } else {
                    "Obtener ubicación actual"
                }
            )
        }

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        OutlinedTextField(
            value =
                latitud ?: "",
            onValueChange = {},
            readOnly = true,
            label = {
                Text("Latitud")
            },
            placeholder = {
                Text(
                    "Sin ubicación disponible"
                )
            },
            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value =
                longitud ?: "",
            onValueChange = {},
            readOnly = true,
            label = {
                Text("Longitud")
            },
            placeholder = {
                Text(
                    "Sin ubicación disponible"
                )
            },
            modifier =
                Modifier.fillMaxWidth()
        )

        mensajeError?.let {
                mensaje ->

            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )

            Text(
                text = mensaje,
                color =
                    MaterialTheme
                        .colorScheme
                        .error
            )
        }

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        TextButton(
            onClick = onBackClick
        ) {

            Text(
                "Volver al menú principal"
            )
        }
    }
}