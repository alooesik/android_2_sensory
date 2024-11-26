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
fun ScreenPressure(navController: NavController, sensorManager: SensorManager) {
    val pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
    var pressure by remember { mutableStateOf(0f) }
    var progress by remember { mutableStateOf(0f) }

    val maxPressure = 1100f

    DisposableEffect(Unit) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val newPressure = event.values.firstOrNull() ?: 0f
                pressure = newPressure
                progress = newPressure.coerceIn(0f, maxPressure) / maxPressure
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        if (pressureSensor != null) {
            sensorManager.registerListener(
                listener,
                pressureSensor,
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
        Text(text = "Ciśnienie atmosferyczne\n", fontSize = 20.sp)
        Text(text = "%.1f hPa".format(pressure), fontSize = 32.sp)

        ProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxSize(0.5f)
        )

        Button(onClick = {
            navController.popBackStack(
                Routes.screenIntroduction,
                inclusive = false
            )
        }) {
            Text(
                text = "Powrót",
                fontSize = 16.sp
            )
        }
    }
}