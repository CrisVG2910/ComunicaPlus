package com.example.comunicaplus.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.comunicaplus.services.SpeechToTextManager

@Composable
fun HablarScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {

    val context =
        LocalContext.current

    var textoReconocido by
    rememberSaveable {
        mutableStateOf("")
    }

    var escuchando by remember {
        mutableStateOf(false)
    }

    var mensajeError by remember {
        mutableStateOf<String?>(null)
    }

    val speechToTextManager =
        remember(context) {

            SpeechToTextManager(
                context = context,

                onResult = { texto ->

                    textoReconocido =
                        texto

                    mensajeError =
                        null
                },

                onStateChange = {
                        estado ->

                    escuchando =
                        estado
                },

                onError = {
                        mensaje ->

                    mensajeError =
                        mensaje
                }
            )
        }

    val permisoMicrofonoLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts
                .RequestPermission()
        ) { concedido ->

            if (concedido) {

                mensajeError =
                    null

                speechToTextManager
                    .iniciarEscucha()

            } else {

                mensajeError =
                    "Debes permitir el acceso al micrófono para utilizar esta función."
            }
        }

    DisposableEffect(
        speechToTextManager
    ) {

        onDispose {

            speechToTextManager
                .liberar()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 24.dp,
                vertical = 24.dp
            ),
        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Hablar",
            fontSize = 30.sp,
            fontWeight =
                FontWeight.Bold,
            color =
                MaterialTheme
                    .colorScheme
                    .primary
        )

        Text(
            text =
                "Permite que otra persona hable y Comunica+ mostrará el mensaje como texto.",
            style =
                MaterialTheme
                    .typography
                    .bodyLarge
        )

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        Button(
            onClick = {

                mensajeError =
                    null

                val permisoConcedido =
                    ContextCompat
                        .checkSelfPermission(
                            context,
                            Manifest.permission.RECORD_AUDIO
                        ) ==
                            PackageManager
                                .PERMISSION_GRANTED

                if (permisoConcedido) {

                    speechToTextManager
                        .iniciarEscucha()

                } else {

                    permisoMicrofonoLauncher
                        .launch(
                            Manifest.permission.RECORD_AUDIO
                        )
                }
            },
            enabled = !escuchando,
            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text(
                if (escuchando) {
                    "Escuchando..."
                } else {
                    "Iniciar escucha"
                }
            )
        }

        if (escuchando) {

            OutlinedButton(
                onClick = {

                    speechToTextManager
                        .detenerEscucha()

                },
                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Text(
                    "Detener escucha"
                )
            }
        }

        Text(
            text =
                "Texto reconocido",
            fontWeight =
                FontWeight.SemiBold
        )

        OutlinedTextField(
            value =
                textoReconocido,
            onValueChange = {},
            readOnly = true,
            placeholder = {

                Text(
                    "Aquí aparecerá lo que se reconozca mediante el micrófono."
                )
            },
            minLines = 5,
            modifier =
                Modifier.fillMaxWidth()
        )

        OutlinedButton(
            onClick = {

                textoReconocido =
                    ""

                mensajeError =
                    null
            },
            enabled =
                textoReconocido
                    .isNotBlank(),
            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text("Limpiar")
        }

        mensajeError?.let {
                mensaje ->

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
                Modifier.height(16.dp)
        )

        TextButton(
            onClick = {

                speechToTextManager
                    .detenerEscucha()

                onBackClick()
            },
            modifier =
                Modifier.align(
                    Alignment
                        .CenterHorizontally
                )
        ) {

            Text(
                "Volver al menú principal"
            )
        }
    }
}