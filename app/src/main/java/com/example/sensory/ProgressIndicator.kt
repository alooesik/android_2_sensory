package com.example.sensory

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProgressIndicator(progress: Float, modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        progress = {
            progress  // wartość od 0 do 1
        },
        modifier = modifier,  // pozwala dostosować wygląd i rozmiar
    )
}