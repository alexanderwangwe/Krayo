package com.krayo.art.ui.screens.onboarding.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingTopBar(
    navController: NavController
) {
    TopAppBar(title = { /*TODO*/ }, navigationIcon = {
        Icon(
            modifier = Modifier.height(40.dp).clickable {
                navController.popBackStack()
            },
            tint = MaterialTheme.colorScheme.onSurface,
            painter = painterResource(id = R.drawable.cancel),
            contentDescription = stringResource(
                id = R.string.go_back
            )
        )
    })
}