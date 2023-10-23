@file:OptIn(ExperimentalMaterial3Api::class)

package com.krayo.art.ui.screens.communities

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.R
import com.krayo.art.ui.screens.home.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CommunitiesScreen(navController: NavController, paddingValues: PaddingValues) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier,

        topBar= {
            TopAppBar(
                title = {
                    Text(text = "Chat")
                },
                actions = {
                    IconButton(
                        onClick = {
                            // Handle the "Add" button click here
                        }
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(50.dp)
                        ) {
                            Icon(
                                modifier = Modifier.padding(10.dp),
                                tint = MaterialTheme.colorScheme.onSurface,
                                painter = painterResource(id = R.drawable.plus_math),
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
    ) {
        // Screen Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
             Image(
                    painter = painterResource(id = R.drawable.binoculars),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Uh Oh",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                )

            Text(
                text = "You have not started any chats yet",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                )
            }
    }
}
