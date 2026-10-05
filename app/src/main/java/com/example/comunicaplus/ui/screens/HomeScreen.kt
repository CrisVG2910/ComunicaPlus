package com.example.comunicaplus.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comunicaplus.model.Usuario

@Composable
fun HomeScreen(
    usuario: Usuario,
    modifier: Modifier = Modifier,
    onEditProfileClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onEscribirClick: () -> Unit = {},
    onHablarClick: () -> Unit = {},
    onBuscarDispositivoClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Comunica+",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Hola, ${usuario.nombre}",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Resumen de preferencias",
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        ResumenUsuario(
            usuario = usuario
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Herramientas de comunicación",
            modifier =
                Modifier.fillMaxWidth(),
            fontWeight =
                FontWeight.Bold
        )

        Text(
            text =
                "Selecciona la herramienta que necesitas.",
            modifier =
                Modifier.fillMaxWidth(),
            style =
                MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        LazyVerticalGrid(
            columns =
                GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            horizontalArrangement =
                Arrangement.spacedBy(12.dp),
            verticalArrangement =
                Arrangement.spacedBy(12.dp),
            userScrollEnabled = false
        ) {

            item {

                Button(
                    onClick =
                        onEscribirClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {

                    Column(
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Escribir",
                            fontWeight =
                                FontWeight.Bold
                        )

                        Text(
                            text = "Texto a voz",
                            style =
                                MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            item {

                Button(
                    onClick =
                        onHablarClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {

                    Column(
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Hablar",
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Voz a texto",
                            style =
                                MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            item {

                Button(
                    onClick =
                        onBuscarDispositivoClick,
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {

                    Column(
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Text("Buscar dispositivo")

                        Text(
                            text = "Próximamente",
                            style =
                                MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedButton(
            onClick = onEditProfileClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Editar perfil")
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedButton(
            onClick = onLogoutClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}

@Composable
private fun ResumenUsuario(
    usuario: Usuario
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(12.dp)
            )
    ) {

        FilaResumen(
            etiqueta = "Nivel auditivo",
            valor = usuario.nivelAuditivo
        )

        HorizontalDivider()

        FilaResumen(
            etiqueta = "Método preferido",
            valor = usuario.metodoComunicacion
        )

        HorizontalDivider()

        FilaResumen(
            etiqueta = "Texto grande",
            valor = if (usuario.textoGrande) {
                "Sí"
            } else {
                "No"
            }
        )

        HorizontalDivider()

        FilaResumen(
            etiqueta = "Vibración",
            valor = if (usuario.vibracion) {
                "Sí"
            } else {
                "No"
            }
        )
    }
}

@Composable
private fun FilaResumen(
    etiqueta: String,
    valor: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = etiqueta,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = valor
        )
    }
}