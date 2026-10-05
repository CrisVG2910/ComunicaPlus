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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comunicaplus.model.Usuario
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    usuario: Usuario,
    modifier: Modifier = Modifier,
    onSaveClick: suspend (Usuario) -> Result<Unit>,
    onDeleteAccountClick: suspend (String) -> Result<Unit>,
    onBackClick: () -> Unit
) {

    val scope = rememberCoroutineScope()

    var nombre by remember {
        mutableStateOf(usuario.nombre)
    }

    val nivelesAuditivos = listOf(
        "Leve",
        "Moderada",
        "Severa",
        "Profunda"
    )

    var nivelAuditivo by remember {
        mutableStateOf(usuario.nivelAuditivo)
    }

    var metodoComunicacion by remember {
        mutableStateOf(usuario.metodoComunicacion)
    }

    var textoGrande by remember {
        mutableStateOf(usuario.textoGrande)
    }

    var vibracion by remember {
        mutableStateOf(usuario.vibracion)
    }

    var menuExpandido by remember {
        mutableStateOf(false)
    }

    var cargando by remember {
        mutableStateOf(false)
    }

    var mensajeError by remember {
        mutableStateOf<String?>(null)
    }

    var mostrarDialogoEliminar by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 32.dp,
                vertical = 24.dp
            ),
        verticalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Editar perfil",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color =
                MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Actualiza tus preferencias de Comunica+",
            style =
                MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
            },
            label = {
                Text("Nombre")
            },
            singleLine = true,
            modifier =
                Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = usuario.correo,
            onValueChange = {},
            readOnly = true,
            label = {
                Text("Correo electrónico")
            },
            supportingText = {
                Text(
                    "El correo de acceso no se modifica desde esta pantalla."
                )
            },
            modifier =
                Modifier.fillMaxWidth()
        )

        Text(
            text = "Nivel de pérdida auditiva",
            fontWeight =
                FontWeight.SemiBold
        )

        ExposedDropdownMenuBox(
            expanded = menuExpandido,
            onExpandedChange = {
                menuExpandido =
                    !menuExpandido
            }
        ) {

            OutlinedTextField(
                value = nivelAuditivo,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(
                        "Seleccionar nivel"
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults
                        .TrailingIcon(
                            expanded =
                                menuExpandido
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

                nivelesAuditivos.forEach {
                        nivel ->

                    DropdownMenuItem(
                        text = {
                            Text(nivel)
                        },
                        onClick = {

                            nivelAuditivo =
                                nivel

                            menuExpandido =
                                false
                        }
                    )
                }
            }
        }

        Text(
            text =
                "Método de comunicación preferido",
            fontWeight =
                FontWeight.SemiBold
        )

        Row(
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            RadioButton(
                selected =
                    metodoComunicacion ==
                            "Texto",
                onClick = {
                    metodoComunicacion =
                        "Texto"
                }
            )

            Text("Texto")
        }

        Row(
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            RadioButton(
                selected =
                    metodoComunicacion ==
                            "Texto y voz",
                onClick = {
                    metodoComunicacion =
                        "Texto y voz"
                }
            )

            Text("Texto y voz")
        }

        Text(
            text =
                "Preferencias de accesibilidad",
            fontWeight =
                FontWeight.SemiBold
        )

        Row(
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Checkbox(
                checked = textoGrande,
                onCheckedChange = {
                    textoGrande = it
                }
            )

            Text(
                "Mostrar textos en tamaño grande"
            )
        }

        Row(
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Checkbox(
                checked = vibracion,
                onCheckedChange = {
                    vibracion = it
                }
            )

            Text(
                "Activar avisos mediante vibración"
            )
        }

        mensajeError?.let {

            Text(
                text = it,
                color =
                    MaterialTheme.colorScheme.error
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = {

                if (nombre.isBlank()) {

                    mensajeError =
                        "El nombre no puede estar vacío."

                    return@Button
                }

                val usuarioActualizado =
                    usuario.copy(
                        nombre = nombre.trim(),
                        nivelAuditivo =
                            nivelAuditivo,
                        metodoComunicacion =
                            metodoComunicacion,
                        textoGrande =
                            textoGrande,
                        vibracion =
                            vibracion
                    )

                scope.launch {

                    cargando = true
                    mensajeError = null

                    val resultado =
                        onSaveClick(
                            usuarioActualizado
                        )

                    if (resultado.isFailure) {

                        mensajeError =
                            resultado
                                .exceptionOrNull()
                                ?.message
                                ?: "No fue posible actualizar el perfil."
                    }

                    cargando = false
                }
            },
            enabled = !cargando,
            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text(
                if (cargando) {
                    "Guardando..."
                } else {
                    "Guardar cambios"
                }
            )
        }

        OutlinedButton(
            onClick = onBackClick,
            enabled = !cargando,
            modifier =
                Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Zona de seguridad",
            fontWeight = FontWeight.Bold,
            color =
                MaterialTheme.colorScheme.error
        )

        OutlinedButton(
            onClick = {
                mostrarDialogoEliminar = true
            },
            enabled = !cargando,
            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Eliminar cuenta",
                color =
                    MaterialTheme.colorScheme.error
            )
        }
    }

    if (mostrarDialogoEliminar) {

        DeleteAccountDialog(
            onDismiss = {
                mostrarDialogoEliminar = false
            },

            onConfirm = { contrasena ->

                scope.launch {

                    cargando = true
                    mensajeError = null

                    val resultado =
                        onDeleteAccountClick(
                            contrasena
                        )

                    if (resultado.isFailure) {

                        mensajeError =
                            resultado
                                .exceptionOrNull()
                                ?.message
                                ?: "No fue posible eliminar la cuenta."

                    } else {

                        mostrarDialogoEliminar =
                            false
                    }

                    cargando = false
                }
            }
        )
    }
}

@Composable
private fun DeleteAccountDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {

    var contrasena by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text("Eliminar cuenta")
        },

        text = {

            Column {

                Text(
                    "Esta acción eliminará permanentemente tu cuenta y tu perfil."
                )

                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )

                OutlinedTextField(
                    value = contrasena,
                    onValueChange = {
                        contrasena = it
                    },
                    label = {
                        Text(
                            "Contraseña actual"
                        )
                    },
                    visualTransformation =
                        PasswordVisualTransformation(),
                    singleLine = true
                )
            }
        },

        confirmButton = {

            TextButton(
                onClick = {
                    onConfirm(contrasena)
                },
                enabled =
                    contrasena.isNotBlank()
            ) {

                Text(
                    text = "Eliminar",
                    color =
                        MaterialTheme.colorScheme.error
                )
            }
        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    )
}