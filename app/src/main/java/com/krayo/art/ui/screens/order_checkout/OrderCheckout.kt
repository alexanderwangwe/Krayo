package com.krayo.art.ui.screens.order_checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.ui.screens.order_checkout.components.BagScreen
import com.krayo.art.ui.screens.order_checkout.components.OrderCheckoutBottomBar
import com.krayo.art.ui.screens.order_checkout.components.OrderCheckoutTopBar
import com.krayo.art.ui.screens.order_checkout.components.OrderCheckoutTopBarState
import com.krayo.art.ui.screens.order_checkout.components.OrderSuccess
import com.krayo.art.ui.screens.order_checkout.components.PaymentScreen
import com.krayo.art.ui.screens.order_checkout.components.ReviewScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderCheckoutScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    updateNavState: (Boolean) -> Unit,
) {
    var currentState by rememberSaveable {
        mutableStateOf(OrderCheckoutTopBarState.Bag)
    }
    updateNavState(false)

    val bottomBarText = when (currentState) {
        OrderCheckoutTopBarState.Bag -> "KES\n5,000"
        OrderCheckoutTopBarState.Payment -> "KES 5,000"
        OrderCheckoutTopBarState.Review -> "Review"
        OrderCheckoutTopBarState.Success -> "Success"
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            OrderCheckoutTopBar(currentState, navController) {
                currentState = it
            }
        },
        bottomBar = {
            OrderCheckoutBottomBar(currentState, navController, bottomBarText) {
                currentState = it
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .padding(top = 15.dp)
                .padding(horizontal = 15.dp)
                .verticalScroll(rememberScrollState())
        ) {
            when (currentState) {
                OrderCheckoutTopBarState.Bag -> BagScreen()
                OrderCheckoutTopBarState.Payment -> PaymentScreen()
                OrderCheckoutTopBarState.Review -> ReviewScreen()
                OrderCheckoutTopBarState.Success -> OrderSuccess()
            }
        }
    }
}
