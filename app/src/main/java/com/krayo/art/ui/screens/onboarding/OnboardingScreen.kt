package com.krayo.art.ui.screens.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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

    val rules = listOf(
        OnboardingRules.First,
        OnboardingRules.Second,
        OnboardingRules.Third,
        OnboardingRules.Fourth,
        OnboardingRules.Fifth,
    )

    Scaffold(
        topBar = {
            OnboardingTopBar(navController = navController)
        },
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding).padding(horizontal = 15.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(50.dp))
            Text(
                textAlign = TextAlign.Start,
                text = "Hey there! We are excited you want to start selling! A few rules before you begin",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            for (rule in rules) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(vertical = 15.dp).fillMaxWidth()
                ) {
                    Text(
                        textAlign = TextAlign.Start,
                        text = rule.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        textAlign = TextAlign.Start,
                        text = rule.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }

            Box(
                modifier = Modifier.padding(top = 60.dp).padding(bottom = 25.dp)
            ) {
                Button(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.Black
                        ),
                        onClick = {
                            navController.navigate(Destinations.CONTENT_CREATION.name)
                        }) {
                        Text(
                            text = "I have understood",
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
        }
    }
}
