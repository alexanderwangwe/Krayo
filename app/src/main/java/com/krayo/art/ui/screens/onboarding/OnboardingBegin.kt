package com.krayo.art.ui.screens.onboarding

import android.content.Context
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.MainActivity
import com.krayo.art.constants.Destinations
import com.krayo.art.ui.screens.onboarding.components.OnboardingTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingBegin(
    navController: NavController,
    paddingValues: PaddingValues,
    context: MainActivity
) {
    Scaffold(
        topBar = {
            OnboardingTopBar(navController = navController)
        },
        modifier = Modifier
            .fillMaxSize().padding(horizontal = 15.dp)
            .absolutePadding(bottom = paddingValues.calculateBottomPadding()),
        containerColor = MaterialTheme.colorScheme.surface
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp),
                    text = "Begin Your Journey",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                )

                Text(
                    modifier = Modifier
                        .padding(top = 40.dp),
                    text = "Kickstart your artistic journey with Krayo by creating your first post. With a pool of artists and art enthusiasts we hope to help you grow and make the most of your talent.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Left,
                )
            }

            Box(
                modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
            ) {
                Column {
                    Text(
                        text = "Why choose to also sell ?",
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Destinations.ONBOARDING_PROCESS.name)
                            },
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium,

                        )

                    Button(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth(),
                        enabled = true,
                        onClick = {
                            onBoardingIsCompleted(context = context)
                            navController.popBackStack()
                            navController.navigate(Destinations.CONTENT_CREATION.name)
                        }) {
                        Text(
                            text = "Create Post",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )
                    }
                }
            }

        }

    }
}

private fun onBoardingIsCompleted(context : MainActivity) {
    val sharedPreferences = context.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
    val  editor = sharedPreferences.edit()
    editor.putBoolean("isComplete", true)
    editor.apply()
}