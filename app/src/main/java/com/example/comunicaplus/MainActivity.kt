package com.example.comunicaplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.comunicaplus.ui.screens.LoginScreen
import com.example.comunicaplus.ui.theme.ComunicaPlusTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            ComunicaPlusTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    LoginScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}