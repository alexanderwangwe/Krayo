package com.krayo.art.ui.screens.dashboard.subscreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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

enum class OrderViewType {
    PENDING_ORDER,
    ONGOING_ORDER,
}

@OptIn(ExperimentalMaterial3Api::class)
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
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopBar(
                type = type,
                navController = navController
            )
        },
        bottomBar = {
            BottomBar(type = type, navController = navController)
        }
    ) { paddingValues ->

    }
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

    TopAppBar(
        navigationIcon = {
            Row(
                modifier = modifier
                    .padding(start = 5.dp),
            ){
                Surface(
                    modifier = modifier.size(40.dp),
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
                    .fillMaxWidth().offset(x = (-20).dp),
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
    Column(
        modifier = modifier.padding(
            bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
        ).padding(horizontal = 15.dp)
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
