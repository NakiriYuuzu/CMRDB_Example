package net.yuuzu.cmrdb_example.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.molecule.collectAction
import moe.tlaster.precompose.molecule.rememberPresenter

@Composable
fun ThirdScreen() {
    val (state, channel) = rememberPresenter { ThirdPresenter(it) }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "消費計算器",
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            OutlinedTextField(
                value = state.cost,
                onValueChange = { channel.trySend(ThirdAction.CostChanged(it)) },
                placeholder = { if (state.cost.isEmpty()) Text("金額") else Text("") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            OutlinedTextField(
                value = state.percentage,
                onValueChange = { channel.trySend(ThirdAction.PercentageChanged(it)) },
                placeholder = { if (state.percentage.isEmpty()) Text("消費百分比") else Text("") },
                suffix = { Text("%") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("進位")
                Switch(
                    checked = state.checked,
                    onCheckedChange = { channel.trySend(ThirdAction.CheckedChanged(it)) },
                )
            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("小費 + 消費 總金額:")
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Text("小費：$${state.tip}")
                    Text("$${state.totalCost}")
                }
            }
        }
        Button(
            onClick = {
                channel.trySend(ThirdAction.Calculate)
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Text("計算")
        }
    }
}

data class ThirdState(
    var cost: String,
    var percentage: String,
    var checked: Boolean,

    var tip: Int,
    var totalCost: Int,
)

sealed interface ThirdAction {
    data class CostChanged(val value: String) : ThirdAction
    data class PercentageChanged(val value: String) : ThirdAction
    data class CheckedChanged(val value: Boolean) : ThirdAction
    data object Calculate : ThirdAction
}

@Composable
fun ThirdPresenter(
    action: Flow<ThirdAction>
): ThirdState {
    var cost by remember { mutableStateOf("") }
    var percentage by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }

    var tip by remember { mutableStateOf(0) }
    var totalCost by remember { mutableStateOf(0) }

    action.collectAction {
        when (this) {
            is ThirdAction.CheckedChanged -> {
                checked = value
            }
            is ThirdAction.CostChanged -> {
                cost = value
            }
            is ThirdAction.PercentageChanged -> {
                percentage = value
            }
            is ThirdAction.Calculate -> {
                val costInt = cost.toIntOrNull() ?: 0
                val percentageInt = percentage.toIntOrNull() ?: 0
                tip = (costInt * percentageInt / 100)
                totalCost = costInt + tip
            }
        }
    }
    return ThirdState(
        checked = checked,
        cost = cost,
        percentage = percentage,
        tip = tip,
        totalCost = totalCost,
    )
}