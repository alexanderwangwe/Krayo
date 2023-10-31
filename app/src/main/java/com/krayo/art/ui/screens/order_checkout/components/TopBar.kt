package com.krayo.art.ui.screens.order_checkout.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.R

enum class OrderCheckoutTopBarState {
    Bag,
    Payment,
    Review,
    Success
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderCheckoutTopBar(
    currentState: OrderCheckoutTopBarState = OrderCheckoutTopBarState.Bag,
    navController: NavController,
    updateCurrentState: (OrderCheckoutTopBarState) -> Unit
) {
    var currentProgress by rememberSaveable { mutableStateOf(0f) }

    currentProgress = when (currentState) {
        OrderCheckoutTopBarState.Bag -> (1f / 4f)
        OrderCheckoutTopBarState.Payment -> (2f / 4f)
        OrderCheckoutTopBarState.Review -> (3f /4f)
        OrderCheckoutTopBarState.Success -> (4f / 4f)
    }

    BackHandler {
        handleBackPress(navController, currentState) {
            updateCurrentState(it)
        }
    }

    val topBarText = when (currentState) {
        OrderCheckoutTopBarState.Bag -> stringResource(id = R.string.checkout)
        OrderCheckoutTopBarState.Payment -> stringResource(id = R.string.payment)
        OrderCheckoutTopBarState.Review -> stringResource(id = R.string.review)
        OrderCheckoutTopBarState.Success -> stringResource(id = R.string.confirmed)
    }

    Column {
        CenterAlignedTopAppBar(
            navigationIcon = {
                Spacer(modifier = Modifier.width(5.dp))
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(40.dp),
                    onClick = {
                        handleBackPress(navController, currentState) {
                            updateCurrentState(it)
                        }
                    }
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(10.dp),
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = stringResource(id = R.string.go_back)
                    )
                }
            },
            title = {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {

                        Text(
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            text = topBarText,
                        )
                    }
                }
            })

        LinearProgressIndicator(
            trackColor = MaterialTheme.colorScheme.background,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 15.dp),
            progress = currentProgress
        )
    }
}

private fun handleBackPress(
    navController: NavController,
    currentState: OrderCheckoutTopBarState,
    updateCurrentState: (OrderCheckoutTopBarState) -> Unit
) {
    when (currentState) {
        OrderCheckoutTopBarState.Bag -> navController.popBackStack()
        OrderCheckoutTopBarState.Payment -> updateCurrentState(OrderCheckoutTopBarState.Bag)
        OrderCheckoutTopBarState.Review -> updateCurrentState(OrderCheckoutTopBarState.Payment)
        OrderCheckoutTopBarState.Success -> updateCurrentState(OrderCheckoutTopBarState.Review)
    }
}
