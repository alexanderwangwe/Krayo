package com.krayo.art.ui.screens.authentication.subscreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.R
import com.krayo.art.constants.Destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthSuccessScreen(
    navController: NavController,
    paddingValues: PaddingValues,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            AuthSuccessTopBar(
                navController = navController,
                paddingValues = paddingValues
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .padding(horizontal = 15.dp, vertical = 50.dp)
        ) {
            InfoSection()
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom,
            ) {
                ContinueButton(
                    navController = navController,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
private fun ContinueButton(
    navController: NavController,
    modifier: Modifier
){
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.Black
        ),
        modifier = modifier
            .fillMaxWidth(),
        onClick = {
            navController.popBackStack(Destinations.HOME.name, false)
        }) {
        Row {
            Text(
                modifier = modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.continue_text),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun InfoSection(
    modifier: Modifier = Modifier,
) {
    Column {
        Text(
            text = stringResource(id = R.string.email_verification_success),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
fun AuthSuccessTopBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(paddingValues)
            .height(50.dp)
            .padding(horizontal = 15.dp),
    ) {
        Surface(
            modifier = modifier
                .clip(CircleShape)
                .clickable {
                    navController.popBackStack(Destinations.HOME.name, false)
                },
            color = MaterialTheme.colorScheme.surface,
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = modifier
                    .size(35.dp),
                painter = painterResource(id = R.drawable.cancel),
                contentDescription = stringResource(
                    id = R.string.cancel
                ),
            )
        }
    }
}
