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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    nombreUsuario: String,
    modifier: Modifier = Modifier,
    onSpeechToTextClick: () -> Unit = {},
    onTextToSpeechClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
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
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Hola, $nombreUsuario",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "¿Cómo quieres comunicarte?",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Button(
            onClick = onSpeechToTextClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Voz a texto")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = onTextToSpeechClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Texto a voz")
        }

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        OutlinedButton(
            onClick = onLogoutClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}