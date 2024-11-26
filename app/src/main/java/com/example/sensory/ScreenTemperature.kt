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
fun ScreenTemperature(navController: NavController, sensorManager: SensorManager) {
    val temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)     // pobranie sensora temperatury otoczenia
    var temperature by remember { mutableStateOf(0f) }                                    // akutalna wartość temperatury
    var progress by remember { mutableStateOf(0f) }                                       // aktualna wartość postępu w zakresie 0-1

    val minTemperature = -273.1f  // minimalna temperatura
    val maxTemperature = 100f     // maksymalna temperatura

    DisposableEffect(Unit) {                                              // uruchomienie i czyszczenie zasobów  w czasie życia Composable
        val listener = object : SensorEventListener {                     // implementacja nasłuchiwania zdarzeń z sensora
            override fun onSensorChanged(event: SensorEvent) {            // wywołanie nasłuchiwania z każdą nową wwartością
                val newTemperature = event.values.firstOrNull() ?: 0f     // pobranie pierwszej wartości z tablicy (jeśli istnieje)
                temperature = newTemperature                              // zapisanie nowej wartości
                progress = ((newTemperature - minTemperature) / (maxTemperature - minTemperature)).coerceIn(0f, 1f)     // przeskalowanie temperatury do zakresu 0-1 (wskaźnik)
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}       // wywoływane w przypadku zmiany dokłądnosci sensora
        }

        if (temperatureSensor != null) {               // sp[rawdzenie czy sensor jest dostępny
            sensorManager.registerListener(            // rejestracja sensora z normalną f_s
                listener,
                temperatureSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        // kod wykonywany podczas zwalniania zasobów, np. wyrejestrowanie listenera
        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Temperatura otoczenia\n", fontSize = 20.sp)
        Text(text = "${temperature.toInt()} °C\n", fontSize = 32.sp)

        ProgressIndicator(
            progress = progress,                            // ustawienie wartości progresu w zakresie 0-1
            modifier = Modifier.fillMaxSize(0.5f)   //  wskaźnik zajmuje 50% rozmiaru kolumny
        )

        Button(onClick = { navController.navigate(Routes.screenLight) }) {
            Text(
                text = "Dalej",
                fontSize = 16.sp
            )
        }
    }
}
