package com.krayo.art.ui.screens.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, paddingValues: PaddingValues, updateNavState: (Boolean) -> Unit) {
    updateNavState(false)
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            DashboardTopBar(
                navController =
                navController,
                modifier = Modifier,
                paddingValues = WindowInsets.statusBars.asPaddingValues()
            )
        },
    ) { innerPadding ->
        // Where is the declaration for this?
        // ScrollContent(innerPadding)
        Column(
            modifier = Modifier.padding(innerPadding).padding(15.dp).fillMaxWidth(),
        ) {
            PendingLabelText()
            PendingOrdersCard()
            Spacer(modifier = Modifier.height(10.dp))
            OngoingLabelText()
            OngoingOrdersCard()
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                onClick = {},
            ){
                Text(
                    text = " Previous Orders",
                    modifier = Modifier
                        .padding(10.dp),
                    textAlign = TextAlign.Center,
                )
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth().padding(top = 10.dp)
                    .height(40.dp),
                onClick = {},
            ){
                Text(
                    text = "Payments",
                    modifier = Modifier
                        .padding(10.dp),
                    textAlign = TextAlign.Center,
                )
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth().padding(top = 10.dp)
                    .height(40.dp),
                onClick = {},
            ){
                Text(
                    text = "Analytics",
                    modifier = Modifier
                        .padding(10.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }

    }
}

@Composable
private fun DashboardTopBar(
    navController: NavController, modifier: Modifier, paddingValues: PaddingValues
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(15.dp),
    ) {
        Surface(
            modifier = modifier.size(40.dp),
            color = MaterialTheme.colorScheme.background,
            shape = CircleShape
        ) {
            Icon(
                modifier = modifier
                    .padding(10.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                tint = MaterialTheme.colorScheme.onSurface,
                painter = painterResource(id = R.drawable.back),
                contentDescription = stringResource(id = R.string.go_back)
            )
        }

        Text(
            modifier = modifier
                .fillMaxWidth()
                .offset(x = -17.5.dp),
            text = stringResource(id = R.string.dashboard),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    }
}


@Composable
fun PendingLabelText() {
    Column {
        Text("Pending Orders", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun PendingOrdersCard() {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .size(width = 240.dp, height = 100.dp).fillMaxWidth().padding(top = 10.dp)
    ) {
        Text(
            text = "",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun OngoingLabelText() {
    Column {
        Text("Ongoing Orders", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun OngoingOrdersCard() {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .size(width = 240.dp, height = 100.dp).fillMaxWidth().padding(top = 10.dp)
    ) {
        Text(
            text = "",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}