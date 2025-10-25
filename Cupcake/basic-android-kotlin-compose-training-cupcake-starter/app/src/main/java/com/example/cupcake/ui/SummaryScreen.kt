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
fun OrderSummaryScreen(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "ORDER SUMMARY")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Quantity: ${orderUiState.quantity}")
        Text(text = "Flavor: ${orderUiState.flavor}")
        Text(text = "Pickup date: ${orderUiState.date}")
        Text(text = "Total: ${orderUiState.price}")

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onCancelButtonClicked
            ) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                modifier = Modifier.weight(1f),
                onClick = { onSendButtonClicked("Subject", "Summary details") }
            ) {
                Text("Send")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderSummaryPreview() {
    CupcakeTheme {
        OrderSummaryScreen(
            orderUiState = OrderUiState(
                quantity = 6,
                flavor = "Chocolate",
                date = "Tomorrow",
                price = "$15.00"
            ),
            onCancelButtonClicked = {},
            onSendButtonClicked = { _, _ -> },
            modifier = Modifier.fillMaxHeight()
        )
    }
}
