package com.example.cupcake.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.NumberFormat

private const val PRICE_PER_CUPCAKE = 2.00
private const val SAME_DAY_PICKUP_SURCHARGE = 3.00

class OrderViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = getPickupOptions()))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    fun setQuantity(numberCupcakes: Int) {
        _uiState.value = _uiState.value.copy(quantity = numberCupcakes)
        updatePrice()
    }

    fun setFlavor(desiredFlavor: String) {
        _uiState.value = _uiState.value.copy(flavor = desiredFlavor)
    }

    fun setDate(pickupDate: String) {
        _uiState.value = _uiState.value.copy(date = pickupDate)
        updatePrice()
    }

    fun resetOrder() {
        _uiState.value = OrderUiState(pickupOptions = getPickupOptions())
    }

    private fun getPickupOptions(): List<String> {
        return listOf("Today", "Tomorrow", "In 2 days")
    }

    private fun updatePrice() {
        val calculatedPrice = (_uiState.value.quantity * PRICE_PER_CUPCAKE) +
                if (_uiState.value.date == getPickupOptions()[0]) SAME_DAY_PICKUP_SURCHARGE else 0.0
        val formattedPrice = NumberFormat.getCurrencyInstance().format(calculatedPrice)
        _uiState.value = _uiState.value.copy(price = formattedPrice)
    }
}
