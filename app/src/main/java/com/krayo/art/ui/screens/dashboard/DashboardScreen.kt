package com.krayo.art.ui.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.krayo.art.R
import com.krayo.art.constants.Destinations
import com.krayo.art.ui.screens.profile.NavDrawerItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, paddingValues: PaddingValues, updateNavState: (Boolean) -> Unit) {
    updateNavState(true)
    var drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val drawerIsOpen = drawerState.currentValue == DrawerValue.Closed

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            DashboardTopBar(
                drawerIsOpen = drawerIsOpen,
                drawerState = drawerState,
                navController = navController,
                scope = scope,
            )

        },
    ) { innerPadding ->
        // Where is the declaration for this?
        // ScrollContent(innerPadding)

        val pendingOrder = OrderUiState(
            orderAmount = "KES 1000",
            orderDate = "12/12/2023",
            orderNumber = "8473990293",
            orderStatus = "Pending",
            orderItem = OrderItem(
                orderItemName = "Spider man piece",
                orderItemDescription = "A piece on spider man flying over manhattan with MJ on his back",
                orderItemPrice = "KES 1000",
            ),
            customer = Customer(
                customerName = "Peter Parker",
                customerPhoneNumber = "0712345678",
                customerEmail = "peter@gmail.com",
                customerAddress = "New York, Manhattan",
            )
        )

        val ongoingOrder = OrderUiState(
            orderAmount = "KES 1000",
            orderDate = "12/12/2023",
            orderNumber = "8473990293",
            orderStatus = "Ongoing",
            orderItem = OrderItem(
                orderItemName = "Spider man piece",
                orderItemDescription = "A piece on spider man flying over manhattan with MJ on his back",
                orderItemPrice = "KES 1000",
            ),
            customer = Customer(
                customerName = "Peter Parker",
                customerPhoneNumber = "0712345678",
                customerEmail = "peter@gmail.com",
                customerAddress = "New York, Manhattan",
            )
        )

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            ModalNavigationDrawer(
                modifier = if (!drawerIsOpen) Modifier.zIndex(1f) else Modifier,
                drawerState = drawerState,
                gesturesEnabled = true,
                drawerContent = {
                    DashboardDrawerContent(
                        scope = scope,
                        drawerState = drawerState,
                        navController = navController
                    )
                }
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Column(
                        modifier = Modifier
                            .padding(top = innerPadding.calculateTopPadding())
                            .padding(horizontal = 15.dp)
                            .fillMaxWidth()
                            .verticalScroll(
                                rememberScrollState()
                            ),
                    ) {
                        PendingLabelText()
                        OrderCard(pendingOrder, navController)
                        Spacer(modifier = Modifier.height(10.dp))
                        OngoingLabelText()
                        OrderCard(ongoingOrder, navController)
                        Spacer(modifier = Modifier.height(75.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardTopBar(
    drawerIsOpen: Boolean,
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavController
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        navigationIcon = {
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.popBackStack()
                    },
                color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                shape = CircleShape
            ) {
                Icon(
                    painterResource(id = R.drawable.back),
                    modifier = Modifier.padding(10.dp),
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = stringResource(
                        id = R.string.go_back
                    )
                )
            }
        },
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (drawerIsOpen)
                    Text(stringResource(id = R.string.dashboard), textAlign = TextAlign.Center)
            }
        },
        actions = {
            if (drawerIsOpen)
                Surface(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable {
                            scope.launch {
                                drawerState.open()
                            }
                        },
                    color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                    shape = CircleShape
                ) {
                    Icon(
                        Icons.Outlined.Menu,
                        modifier = Modifier.padding(10.dp),
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null
                    )
                }
        })
}

enum class DrawerItem {
    PREVIOUS_ORDERS,
    PAYMENTS,
    ANALYTICS,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardDrawerContent(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavController
) {
    var selected by rememberSaveable {
        mutableStateOf(DrawerItem.PREVIOUS_ORDERS)
    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        ModalDrawerSheet(
            modifier = Modifier
                .fillMaxWidth(0.75f)

        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(bottom = 15.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .size(35.dp)
                                .clickable {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                            painter = painterResource(id = R.drawable.cancel),
                            contentDescription = stringResource(
                                id = R.string.cancel
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    NavDrawerItem(
                        name = "Previous Orders",
                        selected = DrawerItem.PREVIOUS_ORDERS == selected
                    ) {
                        selected = DrawerItem.PREVIOUS_ORDERS
                    }
                    NavDrawerItem(
                        name = "Payments",
                        selected = DrawerItem.PAYMENTS == selected
                    ) {
                        selected = DrawerItem.PAYMENTS
                    }

                    NavDrawerItem(
                        name = "Analytics",
                        selected = DrawerItem.ANALYTICS == selected
                    ) {
                        selected = DrawerItem.ANALYTICS
                    }
                }
            }
        }
    }
}


@Composable
fun PendingLabelText() {
    Column {
        Text("Pending Orders (1)", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun OrderCard(order: OrderUiState, navController: NavController) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text = order.orderStatus.uppercase(),
                color = if (order.orderStatus == "Pending") MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "Order #" + order.orderNumber.uppercase(),
                style = MaterialTheme.typography.bodyMedium,
            )

            Row(
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Image(
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 15.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(10.dp)
                        ),
                    painter = painterResource(id = R.drawable.baseline_add_photo_alternate),
                    contentDescription = null
                )
                Column {
                    Text(
                        text = order.orderItem.orderItemName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp,
                    )
                    Text(
                        text = order.orderItem.orderItemDescription,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }

            Divider(
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            Text(
                text = order.customer.customerName,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = order.customer.customerAddress,
                style = MaterialTheme.typography.bodyMedium,
            )

            Divider(
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            Text(
                text = order.orderAmount,
                style = MaterialTheme.typography.bodyLarge,
            )

            Divider(
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (order.orderStatus == "Pending") MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                modifier = Modifier.fillMaxWidth(), onClick = {
                    navController.navigate(Destinations.ORDER_VIEW.name)
                }) {
                Text("View Order", color = Color.Black, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
private fun OngoingLabelText() {
    Column {
        Text("Ongoing Orders (1)", style = MaterialTheme.typography.bodyLarge)
    }
}

data class OrderItem(
    val orderItemName: String,
    val orderItemDescription: String,
    val orderItemPrice: String,
)

data class Customer(
    val customerName: String,
    val customerPhoneNumber: String,
    val customerEmail: String,
    val customerAddress: String,
)

data class OrderUiState(
    val orderNumber: String,
    val orderDate: String,
    val orderStatus: String,
    val orderAmount: String,
    val orderItem: OrderItem,
    val customer: Customer
)
