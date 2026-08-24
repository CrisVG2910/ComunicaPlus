package com.example.comunicaplus.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onRecoverPasswordClick: () -> Unit = {}
) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var recordarUsuario by remember { mutableStateOf(false) }

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

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Comunicación más accesible",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = {
                Text("Correo electrónico")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = {
                Text("Contraseña")
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = recordarUsuario,
                onCheckedChange = {
                    recordarUsuario = it
                }
            )

            Text(
                text = "Recordar usuario"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onRecoverPasswordClick
        ) {
            Text("¿Olvidaste tu contraseña?")
        }

        TextButton(
            onClick = onRegisterClick
        ) {
            Text("Crear una cuenta")
        }
    }
}