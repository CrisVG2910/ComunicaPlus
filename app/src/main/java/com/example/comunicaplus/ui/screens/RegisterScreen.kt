package com.example.comunicaplus.ui.screens
import com.example.comunicaplus.model.Usuario

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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onRegisterClick:
        suspend (Usuario, String) -> Result<Unit>,
    onBackToLoginClick: () -> Unit = {}
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }

    val nivelesAuditivos = listOf(
        "Leve",
        "Moderada",
        "Severa",
        "Profunda"
    )

    var nivelAuditivo by remember {
        mutableStateOf(nivelesAuditivos.first())
    }

    var menuExpandido by remember {
        mutableStateOf(false)
    }

    var metodoComunicacion by remember {
        mutableStateOf("Texto")
    }

    var textoGrande by remember {
        mutableStateOf(false)
    }

    var vibracion by remember {
        mutableStateOf(false)
    }

    var mensajeError by remember {
        mutableStateOf<String?>(null)
    }

    val scope = rememberCoroutineScope()

    var cargando by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 32.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Crear cuenta",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Completa tus datos para registrarte en Comunica+",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
            },
            label = {
                Text("Nombre")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = correo,
            onValueChange = {
                correo = it
            },
            label = {
                Text("Correo electrónico")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
            },
            label = {
                Text("Contraseña")
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = confirmarContrasena,
            onValueChange = {
                confirmarContrasena = it
            },
            label = {
                Text("Confirmar contraseña")
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Nivel de pérdida auditiva",
            fontWeight = FontWeight.SemiBold
        )

        ExposedDropdownMenuBox(
            expanded = menuExpandido,
            onExpandedChange = {
                menuExpandido = !menuExpandido
            }
        ) {

            OutlinedTextField(
                value = nivelAuditivo,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Seleccionar nivel")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = menuExpandido
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = menuExpandido,
                onDismissRequest = {
                    menuExpandido = false
                }
            ) {

                nivelesAuditivos.forEach { nivel ->

                    DropdownMenuItem(
                        text = {
                            Text(nivel)
                        },
                        onClick = {
                            nivelAuditivo = nivel
                            menuExpandido = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Método de comunicación preferido",
            fontWeight = FontWeight.SemiBold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            RadioButton(
                selected = metodoComunicacion == "Texto",
                onClick = {
                    metodoComunicacion = "Texto"
                }
            )

            Text("Texto")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            RadioButton(
                selected = metodoComunicacion == "Texto y voz",
                onClick = {
                    metodoComunicacion = "Texto y voz"
                }
            )

            Text("Texto y voz")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Preferencias de accesibilidad",
            fontWeight = FontWeight.SemiBold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = textoGrande,
                onCheckedChange = {
                    textoGrande = it
                }
            )

            Text("Mostrar textos en tamaño grande")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = vibracion,
                onCheckedChange = {
                    vibracion = it
                }
            )

            Text("Activar avisos mediante vibración")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {

                mensajeError = when {

                    nombre.isBlank() ||
                            correo.isBlank() ||
                            contrasena.isBlank() ||
                            confirmarContrasena.isBlank() -> {
                        "Debes completar todos los campos."
                    }

                    contrasena != confirmarContrasena -> {
                        "Las contraseñas no coinciden."
                    }

                    contrasena.length < 6 -> {
                        "La contraseña debe contener al menos 6 caracteres."
                    }

                    else -> null
                }

                if (mensajeError == null) {

                    val usuario = Usuario(
                        nombre = nombre.trim(),
                        correo = correo.trim(),
                        nivelAuditivo = nivelAuditivo,
                        metodoComunicacion = metodoComunicacion,
                        textoGrande = textoGrande,
                        vibracion = vibracion
                    )

                    scope.launch {

                        cargando = true

                        val resultado =
                            onRegisterClick(
                                usuario,
                                contrasena
                            )

                        if (resultado.isFailure) {
                            mensajeError =
                                resultado
                                    .exceptionOrNull()
                                    ?.message
                                    ?: "No fue posible crear la cuenta."
                        }

                        cargando = false
                    }
                }
            },
            enabled = !cargando,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                if (cargando) {
                    "Creando cuenta..."
                } else {
                    "Crear cuenta"
                }
            )
        }

        if (mensajeError != null) {

            Text(
                text = mensajeError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        TextButton(
            onClick = onBackToLoginClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Ya tengo una cuenta")
        }
    }
}