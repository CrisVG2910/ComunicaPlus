package com.example.comunicaplus.ui.screens

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RecoverPasswordScreen(
    modifier: Modifier = Modifier,
    onRecoverPasswordClick:
        suspend (String) -> Result<Unit>,
    onBackToLoginClick: () -> Unit = {}
) {
    var correo by remember {
        mutableStateOf("")
    }

    var resultadoRecuperacion by remember {
        mutableStateOf<Boolean?>(null)
    }

    val scope = rememberCoroutineScope()

    var solicitudEnviada by remember {
        mutableStateOf(false)
    }

    var mensajeError by remember {
        mutableStateOf<String?>(null)
    }

    var cargando by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Recuperar contraseña",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Ingresa el correo asociado a tu cuenta.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = {
                correo = it
                resultadoRecuperacion = null
            },
            label = {
                Text("Correo electrónico")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                scope.launch {

                    cargando = true
                    mensajeError = null
                    solicitudEnviada = false

                    val resultado =
                        onRecoverPasswordClick(
                            correo
                        )

                    if (resultado.isSuccess) {

                        solicitudEnviada = true

                    } else {

                        mensajeError =
                            resultado
                                .exceptionOrNull()
                                ?.message
                                ?: "No fue posible enviar el correo."
                    }

                    cargando = false
                }
            },
            enabled =
                correo.isNotBlank() &&
                        !cargando,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                if (cargando) {
                    "Enviando..."
                } else {
                    "Recuperar contraseña"
                }
            )
        }

        if (solicitudEnviada) {

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Si el correo está registrado, se enviarán las instrucciones de recuperación.",
                color = MaterialTheme.colorScheme.primary
            )
        }

        mensajeError?.let {

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }

        resultadoRecuperacion?.let { correoExiste ->

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (correoExiste) {
                    "El correo fue encontrado. Se enviarán las instrucciones de recuperación."
                } else {
                    "El correo ingresado no se encuentra registrado."
                },
                color = if (correoExiste) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onBackToLoginClick
        ) {
            Text("Volver al inicio de sesión")
        }
    }
}