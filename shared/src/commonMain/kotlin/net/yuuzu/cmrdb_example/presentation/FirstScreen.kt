package net.yuuzu.cmrdb_example.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.molecule.producePresenter

@Composable
fun FirstScreen() {
    val state by producePresenter { TestPresenter() }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Text(text = "Count: ${state.count}")
            Row {
                Button(
                    onClick = {state.action(TestEvent.Increment)},
                ) {
                    Text(text = "Increment")
                }
                Button(
                    onClick = {state.action(TestEvent.Decrement)},
                ) {
                    Text(text = "Decrement")
                }
            }
        }
    }
}

// State
data class TestState(
    val count: Int,
    val action: (TestEvent) -> Unit,
)

// Event
sealed interface TestEvent {
    data object Increment : TestEvent
    data object Decrement : TestEvent
}

@Composable
fun TestPresenter(): TestState {
    var count by remember { mutableStateOf(0) }

    return TestState(count) { event ->
        when (event) {
            is TestEvent.Increment -> count++
            is TestEvent.Decrement -> count--
        }
    }
}