package net.yuuzu.cmrdb_example.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.molecule.collectAction
import moe.tlaster.precompose.molecule.rememberNestedPresenter
import moe.tlaster.precompose.molecule.rememberPresenter

@Composable
fun FourScreen() {
    val snackbar = remember { SnackbarHostState() }
    val (state, channel) = rememberNestedPresenter { FourPresenter(it) }

    LaunchedEffect(state.guessResult) {
        if (state.guessResult.isNotBlank()) {
            if (state.guessResult == "Bingo!") {
                val snackbarResult = snackbar.showSnackbar(
                    message = "${state.guessResult} You guessed ${state.guessCount} times",
                    actionLabel = "RESET",
                    withDismissAction = true,
                    duration = SnackbarDuration.Indefinite
                )
                if (snackbarResult == SnackbarResult.ActionPerformed ||
                    snackbarResult == SnackbarResult.Dismissed) {
                    channel.trySend(FourAction.Reset)
                }
            } else {
                snackbar.showSnackbar(
                    message = "${state.guessResult} You guessed ${state.guessCount} times",
                    duration = SnackbarDuration.Short
                )
            }
            channel.trySend(FourAction.Guessed)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbar) }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = state.guessInput,
                onValueChange = {
                    channel.trySend(FourAction.NumberChanged(it))
                },
                placeholder = { "Enter Here" },
                modifier = Modifier
            )
            Button(
                onClick = { channel.trySend(FourAction.Guess) }
            ) {
                Text(text = "Guess")
            }
            Button(
                onClick = { channel.trySend(FourAction.Reset) }
            ) {
                Text(text = "Reset")
            }
        }
    }
}

data class FourState(
    val guessInput: String,
    val guessNumber: Int,
    val guessCount: Int,
    val guessResult: String,
)

sealed interface FourAction {
    data class NumberChanged(val number: String) : FourAction
    data object Guess : FourAction
    data object Guessed : FourAction
    data object Reset : FourAction
}

@Composable
fun FourPresenter(
    action: Flow<FourAction>
): FourState {
    var targetNumber by remember { mutableStateOf((1..9999).random()) }

    var guessInput by remember { mutableStateOf("") }
    var guessNumber by remember { mutableStateOf(0) }
    var guessCount by remember { mutableStateOf(0) }
    var guessResult by remember { mutableStateOf("") }

    println("targetNumber: $targetNumber")

    action.collectAction {
        when (this) {
            is FourAction.NumberChanged -> {
                println(number)
                println(guessInput)
                guessInput = number
                println(guessInput)
            }
            FourAction.Guess -> {
                guessNumber = guessInput.toInt()
                guessResult = if (guessNumber == targetNumber) {
                    "Bingo!"
                } else {
                    if (guessNumber > targetNumber) {
                        "Too Big!"
                    } else {
                        "Too Small!"
                    }
                }
                guessCount++
            }
            FourAction.Guessed -> {
                guessResult = ""
            }
            FourAction.Reset -> {
                targetNumber = (1..9999).random()
                guessCount = 0
                guessResult = ""
                guessInput = ""
            }
        }
    }

    return FourState(
        guessInput = guessInput,
        guessNumber = guessNumber,
        guessCount = guessCount,
        guessResult = guessResult,
    )
}