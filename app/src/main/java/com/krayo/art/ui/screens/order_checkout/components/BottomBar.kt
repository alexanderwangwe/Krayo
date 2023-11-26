package com.krayo.art.ui.screens.order_checkout.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.krayo.art.R
import com.krayo.art.constants.Destinations
import kotlinx.coroutines.Job

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrderCheckoutBottomBar(
    currentState: OrderCheckoutTopBarState = OrderCheckoutTopBarState.Bag,
    navController: NavController,
    bottomBarText: String,
    updateCurrentState: (OrderCheckoutTopBarState) -> Unit
) {
    val haptics = LocalHapticFeedback.current
    var timeLeft by rememberSaveable { mutableIntStateOf(5) }
    val scope = rememberCoroutineScope()
    var job: Job? = null

    val openAlertDialog = remember { mutableStateOf(false) }

    when {
        openAlertDialog.value -> {
            AlertDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                    updateCurrentState(OrderCheckoutTopBarState.Success)
                },
                dialogTitle = "Confirm",
                dialogText = "Are you sure you want to place this order?",
                icon = Icons.Default.Info
            )
        }
    }

    BottomAppBar(
        containerColor = Color.Transparent, modifier = Modifier.padding(horizontal = 5.dp)
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black
            ),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp),
            onClick = {
                when (currentState) {
                    OrderCheckoutTopBarState.Bag -> {
                        updateCurrentState(OrderCheckoutTopBarState.Payment)
                    }

                    OrderCheckoutTopBarState.Payment -> {
                        updateCurrentState(OrderCheckoutTopBarState.Review)
                    }

                    OrderCheckoutTopBarState.Review -> {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        openAlertDialog.value = true
                    }

                    OrderCheckoutTopBarState.Success -> {
                        navController.popBackStack(Destinations.HOME.name, false)
                    }
                }
            }
        ) {
            when (currentState) {
                OrderCheckoutTopBarState.Bag -> {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.proceed_to),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = stringResource(id = R.string.payment),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                OrderCheckoutTopBarState.Payment -> {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.proceed_to),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = stringResource(id = R.string.review),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                OrderCheckoutTopBarState.Review -> Text(
                    text = "Place Order",
                    style = MaterialTheme.typography.bodyLarge
                )

                OrderCheckoutTopBarState.Success -> Text(
                    text = "Done", style = MaterialTheme.typography.bodyLarge
                )
            }

            Icon(Icons.Outlined.ArrowForward, contentDescription = null)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = null)
        },
        title = {
            Text(text = dialogTitle, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Text(text = dialogText, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Place Order", style = MaterialTheme.typography.bodyMedium)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss", style = MaterialTheme.typography.bodyMedium)
            }
        }
    )
}
