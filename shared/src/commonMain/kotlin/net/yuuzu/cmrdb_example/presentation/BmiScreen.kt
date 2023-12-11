package net.yuuzu.cmrdb_example.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BmiScreen(
    snackbarHost: SnackbarHostState
) {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var data by remember { mutableStateOf("") }

    LaunchedEffect(error) {
        if (error.isNotBlank()) {
            snackbarHost.showSnackbar(message = error, duration = SnackbarDuration.Short)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "BMI", modifier = Modifier.align(Alignment.TopStart).padding(16.dp))
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text(text = "Weight") }
        )
        Spacer(modifier = Modifier.padding(16.dp))
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text(text = "Height") }
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(onClick = {
            if (weight.isBlank() || height.isBlank()) {
                error = "Please input weight and height"
            } else {
                // check is number
                val weightNumber = weight.toDoubleOrNull()
                val heightNumber = height.toDoubleOrNull()
                if (weightNumber == null || heightNumber == null) {
                    error = "Please input number"
                } else {
                    // calculate bmi by cm and kg
                    val bmi = weightNumber / (heightNumber * heightNumber) * 10000
                    data = bmi.toString()
                }
            }
        }) {
            Text(text = "Calculate")
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "Your BMI is $data")
    }
}