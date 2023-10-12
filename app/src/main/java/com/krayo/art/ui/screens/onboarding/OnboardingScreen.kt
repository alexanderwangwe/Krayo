package com.krayo.art.ui.screens.onboarding

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.krayo.art.constants.Destinations

@Composable
fun OnboardingScreen(navController: NavController, paddingValues: PaddingValues) {

    val navController = rememberNavController()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(bottom = paddingValues.calculateBottomPadding()),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome to Krayo",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 153.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )

            Text(
                text = "What are you trying to achieve?",
                modifier = Modifier
                    .width(302.dp)

                    .padding(top = 104.dp),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight(700),

            )

            Button(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color(0xFFD9D9D9),
                        shape = RoundedCornerShape(size = 15.dp)
                    )
                    .height(50.dp)
                    .width(320.dp),
                    //.padding(top = 90.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                enabled = true,
                onClick = {
                    navController.navigate(Destinations.ONBOARDINGPROCESS.name)
                },

            ) {
                Text(text = "Post and sell your creations")
            }

            Button(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color(0xFFD9D9D9),
                        shape = RoundedCornerShape(size = 15.dp)
                    )
                    .height(50.dp)
                    .width(320.dp),
                    //.padding(top = 108.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                enabled = true,
                onClick = {
                    navController.navigate(Destinations.ONBOARDINGPROCESS.name)
                },

                ) {
                Text(text = "Simply just post your artwork")
                //Icon(painter = painterResource(id = R.drawable.carbon_next_outline),
                //contentDescription = null)
            }
        }


    }
}


