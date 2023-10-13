package com.krayo.art.ui.screens.onboarding

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    context: MainActivity
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(bottom = paddingValues.calculateBottomPadding()),
        containerColor = MaterialTheme.colorScheme.surface
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                "Welcome to Krayo",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 140.dp),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )

            Text(
                text = "What are you trying to achieve?",
                modifier = Modifier
                    .width(302.dp)
                    .padding(top = 104.dp),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight(700),

            )

            Box(modifier = Modifier
                .padding(top = 60.dp)
            ){
                Button(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = Color(0xFFD9D9D9),
                            shape = RoundedCornerShape(size = 15.dp)
                        )
                        .height(50.dp)
                        .width(320.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    onClick = {
                        navController.navigate(Destinations.ONBOARDING_PROCESS.name)
                    }

                ) {
                    Text(
                        text = "Post and sell your creations",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Box(modifier = Modifier
                .padding(top = 50.dp)
            ){
                Button(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = Color(0xFFD9D9D9),
                            shape = RoundedCornerShape(size = 15.dp)
                        )
                        .height(50.dp)
                        .width(320.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    onClick = {
                        navController.navigate(Destinations.ONBOARDING_BEGIN.name)
                    },

                    ) {
                    Text(
                        text = "Simply just post your artwork",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    /* Icon(
                        painter = painterResource(id = R.drawable.carbon_next_outline),
                        contentDescription = null
                    ) */

                }
            }

        }

    }
}


