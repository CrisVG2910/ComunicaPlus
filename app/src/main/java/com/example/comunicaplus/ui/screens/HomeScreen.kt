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
    onLogoutClick: () -> Unit = {}
) {

    val funcionesProyectadas = listOf(
        "Voz a texto",
        "Texto a voz"
    )

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
            text = "Funciones de comunicación",
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Disponibles en próximas etapas",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            userScrollEnabled = false
        ) {

            items(funcionesProyectadas) { funcion ->

                Button(
                    onClick = {},
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                ) {

                    Text(
                        text = "$funcion\nPróximamente",
                        textAlign = TextAlign.Center
                    )
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