package com.krayo.art.ui.screens.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.MainActivity
import com.krayo.art.R
import com.krayo.art.constants.Destinations
import com.krayo.art.ui.screens.onboarding.components.OnboardingTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    context: MainActivity,
    updateNavState: (Boolean) -> Unit
) {
    updateNavState(false)

    Scaffold(
        topBar = {
            OnboardingTopBar(navController = navController)
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
            .absolutePadding(bottom = paddingValues.calculateBottomPadding()),
        containerColor = MaterialTheme.colorScheme.surface
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
                    .padding(top = 50.dp)
                    .weight(1f),
            ) {

                Text(
                    textAlign = TextAlign.Start,
                    text = "Hey there! What are you trying to achieve on Krayo?",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Column(
                modifier = Modifier.padding(bottom = 25.dp + padding.calculateBottomPadding()),
            ) {
                Box(
                    modifier = Modifier.padding(top = 60.dp)
                ) {
                    Button(modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.Black
                        ),
                        onClick = {
                            navController.navigate(Destinations.ONBOARDING_PROCESS.name)
                        }) {
                        Text(
                            text = "Post and sell your creations",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.navigate_next_24),
                            contentDescription = stringResource(
                                id = R.string.next
                            )
                        )
                    }
                }

                Box(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        onClick = {
                            navController.navigate(Destinations.ONBOARDING_BEGIN.name)
                        },

                        ) {
                        Text(
                            color = MaterialTheme.colorScheme.onSurface,
                            text = "Just post your creations",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Icon(
                            tint = MaterialTheme.colorScheme.onSurface,
                            painter = painterResource(id = R.drawable.navigate_next_24),
                            contentDescription = stringResource(
                                id = R.string.next
                            )
                        )
                    }
            }
            }
        }

    }
}
