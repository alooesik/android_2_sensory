package com.example.sensory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ScreenIntroduction(navController: NavController) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Sensory", fontSize = 32.sp)
        Text(text = "Czujnik temperatury, światła i ciśnienia", fontSize = 20.sp)
        Text(text = "Aleksandra Becmer\n", fontSize = 16.sp)

        Button(onClick = { navController.navigate(Routes.screenTemperature) }) {
            Text(
                text = "Dalej",
                fontSize = 16.sp
            )
        }
    }
}
