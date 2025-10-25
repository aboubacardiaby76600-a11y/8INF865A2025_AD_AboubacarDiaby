package com.example.cupcake.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cupcake.ui.theme.CupcakeTheme

@Composable
fun SelectOptionScreen(
    subtotal: String,
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {},
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = "Subtotal: $subtotal")
        options.forEach { option ->
            Text(text = option)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onCancelButtonClicked
            ) {
                Text("Cancel")
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = onNextButtonClicked
            ) {
                Text("Next")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectOptionPreview() {
    CupcakeTheme {
        SelectOptionScreen(
            subtotal = "299.99",
            options = listOf("Option 1", "Option 2", "Option 3", "Option 4"),
            onCancelButtonClicked = {},
            onNextButtonClicked = {},
            modifier = Modifier.fillMaxHeight()
        )
    }
}
