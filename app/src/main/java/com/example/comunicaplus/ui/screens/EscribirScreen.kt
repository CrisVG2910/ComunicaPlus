package com.example.comunicaplus.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.comunicaplus.services.TextToSpeechManager

@Composable
fun EscribirScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {

    val context =
        LocalContext.current

    val textToSpeechManager =
        remember(context) {
            TextToSpeechManager(context)
        }

    var texto by rememberSaveable {
        mutableStateOf("")
    }

    var mensajeEstado by remember {
        mutableStateOf<String?>(null)
    }

    /*
     * Liberamos los recursos del motor TTS
     * cuando la pantalla deja de existir.
     */
    DisposableEffect(
        textToSpeechManager
    ) {

        onDispose {

            textToSpeechManager
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
            text = "Escribir",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color =
                MaterialTheme.colorScheme.primary
        )

        Text(
            text =
                "Escribe un mensaje y Comunica+ lo reproducirá en voz alta.",
            style =
                MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedTextField(
            value = texto,
            onValueChange = { nuevoTexto ->

                if (
                    nuevoTexto.length <= 500
                ) {

                    texto = nuevoTexto
                    mensajeEstado = null
                }
            },
            label = {
                Text("Mensaje")
            },
            placeholder = {
                Text(
                    "Ejemplo: Hola, ¿puedes ayudarme por favor?"
                )
            },
            supportingText = {
                Text(
                    "${texto.length} / 500 caracteres"
                )
            },
            minLines = 5,
            maxLines = 10,
            modifier =
                Modifier.fillMaxWidth()
        )

        Button(
            onClick = {

                val resultado =
                    textToSpeechManager
                        .hablar(texto)

                mensajeEstado =
                    if (resultado.isSuccess) {

                        "Reproduciendo mensaje..."

                    } else {

                        resultado
                            .exceptionOrNull()
                            ?.message
                            ?: "No fue posible reproducir el mensaje."
                    }
            },
            enabled = texto.isNotBlank(),
            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text("Reproducir mensaje")
        }

        Row(
            modifier =
                Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            OutlinedButton(
                onClick = {

                    textToSpeechManager
                        .detener()

                    mensajeEstado =
                        "Reproducción detenida."
                },
                modifier =
                    Modifier.weight(1f)
            ) {

                Text("Detener")
            }

            OutlinedButton(
                onClick = {

                    textToSpeechManager
                        .detener()

                    texto = ""
                    mensajeEstado = null
                },
                enabled =
                    texto.isNotEmpty(),
                modifier =
                    Modifier.weight(1f)
            ) {

                Text("Limpiar")
            }
        }

        mensajeEstado?.let { mensaje ->

            Text(
                text = mensaje,
                color =
                    MaterialTheme.colorScheme.primary,
                style =
                    MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        TextButton(
            onClick = {

                textToSpeechManager
                    .detener()

                onBackClick()
            },
            modifier =
                Modifier.align(
                    Alignment.CenterHorizontally
                )
        ) {

            Text(
                "Volver al menú principal"
            )
        }
    }
}