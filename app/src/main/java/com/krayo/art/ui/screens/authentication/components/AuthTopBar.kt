package com.krayo.art.ui.screens.authentication.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.R

@Composable
fun AuthTopBar(
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
                .height(35.dp)
                .clickable {
                    navController.popBackStack()
                },
            color = MaterialTheme.colorScheme.onBackground,
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = modifier
                    .padding(10.dp)
                    .size(15.dp),
                painter = painterResource(id = R.drawable.back),
                contentDescription = stringResource(
                    id = R.string.go_back
                ),
            )
        }
    }
}