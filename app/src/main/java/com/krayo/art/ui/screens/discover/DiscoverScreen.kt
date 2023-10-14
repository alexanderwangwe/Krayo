package com.krayo.art.ui.screens.discover

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.krayo.art.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(navController: NavController, paddingValues: PaddingValues) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    )
                    {
                        Text(
                            modifier = Modifier.padding(end = 10.dp),
                            style = MaterialTheme.typography.titleLarge,
                            text = "Trending"
                        )
                        Text(
                            text = "Events",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                },
            )
        },
    ) { paddingValues_ ->
        TrendingGrid(paddingValues_)
    }
}

@Composable
fun TrendingAppBar(
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.padding(15.dp)
    ) {
        Text(
            text = "Trending",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(end = 10.dp)
        )
        Text(
            text = "Events",
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun NothingTrending() {
    Column(
        modifier = Modifier.padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_camera),
            contentDescription = null
        )
        Text(
            text = "That's weird",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            fontSize = 25.sp,
        )

        Text(
            "Looks like nothing is trending right",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
        Text(
            text = "now"
        )
    }
}

@Composable
fun TrendingGrid(
    paddingValues: PaddingValues
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues).padding(horizontal = 15.dp),
        columns = GridCells.Fixed(2),
        content = {
            items(count = 51) { i ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(0.6f).clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.onBackground)
                )
            }
        }
    )
}