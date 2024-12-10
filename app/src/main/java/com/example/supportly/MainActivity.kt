package com.example.supportly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.supportly.ui.theme.SupportlyTheme
import com.example.supportly.ui.view.Navegation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SupportlyTheme {
                    AppNavigation()
                }
            }
        }
    }
