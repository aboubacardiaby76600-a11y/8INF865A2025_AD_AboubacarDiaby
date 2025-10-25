package com.example.cupcake

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.theme.CupcakeTheme

enum class CupcakeScreen {
    Start,
    Flavor,
    Pickup,
    Summary
}

@Composable
fun CupcakeApp() {
    val viewModel: OrderViewModel = viewModel()
    val navController = rememberNavController()
    val uiState by viewModel.uiState

    CupcakeTheme {
        androidx.compose.material3.Scaffold { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = CupcakeScreen.Start.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = CupcakeScreen.Start.name) {
                    StartOrderScreen(
                        quantityOptions = DataSource.quantityOptions,
                        onNextButtonClicked = { navController.navigate(CupcakeScreen.Flavor.name) },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R.dimen.padding_medium))
                    )
                }
                composable(route = CupcakeScreen.Flavor.name) {
                    val context = LocalContext.current
                    SelectOptionScreen(
                        subtotal = uiState.price,
                        options = DataSource.flavors.map { id -> context.resources.getString(id) },
                        onSelectionChanged = { viewModel.setFlavor(it) },
                        onNextButtonClicked = { navController.navigate(CupcakeScreen.Pickup.name) },
                        modifier = Modifier.fillMaxHeight()
                    )
                }
                composable(route = CupcakeScreen.Pickup.name) {
                    SelectOptionScreen(
                        subtotal = uiState.price,
                        options = uiState.pickupOptions,
                        onSelectionChanged = { viewModel.setDate(it) },
                        onNextButtonClicked = { navController.navigate(CupcakeScreen.Summary.name) },
                        modifier = Modifier.fillMaxHeight()
                    )
                }
                composable(route = CupcakeScreen.Summary.name) {
                    OrderSummaryScreen(
                        orderUiState = uiState,
                        onCancelButtonClicked = {
                            viewModel.resetOrder()
                            navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)
                        },
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            }
        }
    }
}
