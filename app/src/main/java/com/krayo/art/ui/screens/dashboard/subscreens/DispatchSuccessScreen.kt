package com.krayo.art.ui.screens.dashboard.subscreens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DispatchSuccessScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    updateNavState: (Boolean) -> Unit,
) {
    updateNavState(false)
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopBar(
                navController = navController
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp).padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()),
                onClick = { navController.popBackStack(Destinations.DASHBOARD.name, false) }) {
                Text(
                    text = stringResource(id = R.string.done),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .padding(top = 50.dp)
        ) {
            Text(
                text = stringResource(id = R.string.dispatch_success),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    TopAppBar(
        navigationIcon = {
            Row(
                modifier = modifier
                    .padding(start = 10.dp),
            ){
                Surface(
                    modifier = modifier.size(35.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface,
                    onClick = {
                        navController.popBackStack(Destinations.DASHBOARD.name, false)
                    }) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onSurface,
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = stringResource(
                            id = R.string.go_back
                        )
                    )
                }
            }

        },
        title = {
            // Nothing here
        })
}
