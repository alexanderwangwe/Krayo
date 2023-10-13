package com.krayo.art.ui.screens.onboarding

import android.content.Context
import androidx.compose.foundation.border
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
fun OnboardingBegin(
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 46.dp)
                    .padding(top = 190.dp),
                text = "Begin Your Journey",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.onPrimary

            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 46.dp)
                    .padding(top = 40.dp)
                    .width(302.dp),
                text = "Kickstart your artistic journey with Krayo by creating your first post. With a pool of artists and art enthusiasts we hope to help you grow and make the most of your talent.",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Text(text = "Why choose to also sell ?",
                modifier = Modifier
                    .padding(top = 55.dp)
                    .clickable {
                        navController.navigate(Destinations.ONBOARDING_PROCESS.name)
                    },
                textAlign = TextAlign.Center,
                color = Color(0xFF30D69A),
                style = MaterialTheme.typography.bodyMedium,

            )

            Box(modifier = Modifier
                .padding(top = 120.dp)
            ){
                Button(
                    modifier = Modifier
                        .height(50.dp)
                        .width(320.dp)
                    ,
                    shape = RoundedCornerShape(size = 15.dp),
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

private fun onBoardingIsCompleted(context : MainActivity) {
    val sharedPreferences = context.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
    val  editor = sharedPreferences.edit()
    editor.putBoolean("isComplete", true)
    editor.apply()
}