package com.example.sensory

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ScreenLight(navController: NavController, sensorManager: SensorManager) {
    val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    var lightLevel by remember { mutableStateOf(0f) }
    var progress by remember { mutableStateOf(0f) }

    // maksymalne wykrywalne natężenie światła to 40000 lx
    val maxLightLevel = 40000f

    DisposableEffect(Unit) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val newLightLevel = event.values.firstOrNull() ?: 0f
                lightLevel = newLightLevel
                progress = newLightLevel.coerceIn(0f, maxLightLevel) / maxLightLevel
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        if (lightSensor != null) {
            sensorManager.registerListener(
                listener,
                lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Natężenie światła\n", fontSize = 20.sp)
        Text(text = "${lightLevel.toInt()} lx\n", fontSize = 32.sp)

        ProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxSize(0.5f)
        )

        Button(onClick = { navController.navigate(Routes.screenPressure) }) {
            Text(
                text = "Dalej",
                fontSize = 16.sp
            )
        }
    }
}