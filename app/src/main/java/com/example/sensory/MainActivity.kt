package com.example.sensory

import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Routes.screenIntroduction
            ) {
                composable(Routes.screenIntroduction) {
                    ScreenIntroduction(navController)
                }
                composable(Routes.screenTemperature) {
                    ScreenTemperature(navController, getSystemService(SENSOR_SERVICE) as SensorManager)
                }
                composable(Routes.screenLight) {
                    ScreenLight(navController, getSystemService(SENSOR_SERVICE) as SensorManager)
                }
                composable(Routes.screenPressure) {
                    ScreenPressure(navController, getSystemService(SENSOR_SERVICE) as SensorManager)
                }
            }
        }
    }
}
