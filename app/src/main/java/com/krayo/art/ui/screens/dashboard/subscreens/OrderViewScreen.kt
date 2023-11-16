package com.krayo.art.ui.screens.dashboard.subscreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.R
import com.krayo.art.constants.Destinations
import com.krayo.art.ui.screens.order_checkout.components.AmountPayableCard
import com.krayo.art.ui.screens.order_checkout.components.DeliveryCard
import com.krayo.art.ui.screens.order_checkout.components.OrderSummaryCard
import com.krayo.art.ui.screens.order_checkout.components.PaymentMethodCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class OrderViewType {
    PENDING_ORDER,
    ONGOING_ORDER,
}

enum class OrderStatus {
    IN_TRANSIT,
    CANCELLED,
    DISPATCHED,
    DELIVERED,
    DELAYED,
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalStdlibApi::class
)
@Composable
fun OrderViewScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    updateNavState: (Boolean) -> Unit,
) {
    updateNavState(false)

    var type by rememberSaveable {
        mutableStateOf(OrderViewType.PENDING_ORDER)
    }
    var orderStatus by rememberSaveable {
        mutableStateOf(OrderStatus.DISPATCHED)
    }
    val orderState = OrderStatus.values().toList()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            LazyColumn(
                modifier = Modifier.padding(15.dp),
            ) {
                item {
                    Text(
                        text = "Select Status",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Divider(
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
                items(orderState.size) {
                    Text(
                        text = orderState[it].name,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.clickable {
                            orderStatus = orderState[it]
                            scope.launch {
                                sheetState.hide()
                            }
                        })
                    Spacer(modifier = Modifier.padding(vertical = 5.dp))
                }
            }
        },
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface,
            topBar = {
                TopBar(
                    type = type,
                    navController = navController
                )
            },
            bottomBar = {
                if (type == OrderViewType.PENDING_ORDER)
                    BottomBar(type = type, navController = navController)
            }
        ) { paddingValuesScaffold ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValuesScaffold)
                    .padding(horizontal = 15.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                if (type == OrderViewType.ONGOING_ORDER)
                    StatusUpdate(
                        sheetState = sheetState,
                        scope = scope,
                        currentStatus = orderStatus,
                    )
                DeliveryCard(canChangeDelivery = false)
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                OrderSummaryCard()
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                AmountPayableCard()
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                PaymentMethodCard()
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun StatusUpdate(
    sheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    currentStatus: OrderStatus,
) {
    Text("Status", style = MaterialTheme.typography.bodyLarge)
    TextField(value = currentStatus.name, onValueChange = {
        // Do nothing
    }, modifier = Modifier
        .fillMaxWidth()
        .clickable {
            scope.launch {
                sheetState.show()
            }
        }, enabled = false, trailingIcon = {
        Icon(
            Icons.Outlined.KeyboardArrowDown,
            contentDescription = null
        )
    },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = when (currentStatus) {
                OrderStatus.IN_TRANSIT -> MaterialTheme.colorScheme.background
                OrderStatus.CANCELLED -> MaterialTheme.colorScheme.error
                OrderStatus.DISPATCHED -> MaterialTheme.colorScheme.background
                OrderStatus.DELIVERED -> MaterialTheme.colorScheme.secondary
                OrderStatus.DELAYED -> MaterialTheme.colorScheme.error
            },
        )
    )
    Spacer(modifier = Modifier.padding(vertical = 15.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    type: OrderViewType,
    navController: NavController,
) {
    val wordingStringResource = when (type) {
        OrderViewType.PENDING_ORDER -> R.string.pending_order
        OrderViewType.ONGOING_ORDER -> R.string.ongoing_order
    }

    CenterAlignedTopAppBar(
        navigationIcon = {
            Row(
                modifier = modifier
                    .padding(start = 5.dp),
            ) {
                Surface(
                    modifier = modifier.size(35.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.background,
                    onClick = {
                        navController.popBackStack()
                    }) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.padding(10.dp),
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = stringResource(
                            id = R.string.go_back
                        )
                    )
                }
            }

        },
        title = {
            Box(
                modifier = modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = wordingStringResource))
            }
        })
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    type: OrderViewType,
    navController: NavController,
) {
    val isOrderPending = type == OrderViewType.PENDING_ORDER
    val style = MaterialTheme.typography.bodyLarge
    val color = Color.Black
    val btnModifier = modifier
        .fillMaxWidth()
    Divider(
        color = MaterialTheme.colorScheme.background,
    )
    Column(
        modifier = modifier
            .padding(
                bottom = WindowInsets.navigationBars
                    .asPaddingValues()
                    .calculateBottomPadding()
            )
            .padding(horizontal = 15.dp)
            .padding(top = 10.dp)
    ) {
        if (isOrderPending) {
            Button(
                modifier = btnModifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ), onClick = {
                    navController.navigate(Destinations.DISPATCH_SUCCESS.name)
                }) {
                Text(stringResource(id = R.string.dispatch_order), style = style, color = color)
            }
            Button(
                modifier = btnModifier,colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            ), onClick = { /*TODO*/ }) {
                Text(stringResource(id = R.string.cancel_order), style = style, color = color)
            }
        } else
            Button(
                modifier = btnModifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background
                ), onClick = { /*TODO*/ }) {
                Text(
                    stringResource(id = R.string.ongoing_order_progress),
                    style = style,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
    }
}
