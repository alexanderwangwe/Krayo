package com.krayo.art.ui.screens.discover

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krayo.art.R
import com.krayo.art.ui.theme.fontFamily
import androidx.navigation.NavController

@Composable
fun DiscoverScreen(navController: NavController, paddingValues: PaddingValues){
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp, start = 10.dp)
            ) {
                Text(
                    text = "Trending",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text(
                    text = "Events",
                    fontSize = 20.sp,
                )
            }
            TrendingGrid()

    }
}

@Composable
fun NothingTrending(){
    Column(
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
fun TrendingGrid() {
    LazyVerticalGrid(
        modifier= Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        columns = GridCells.Fixed(3),
        content = {
            items(count = 51){ i ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(1f)
                        .background(Color.LightGray)
                )
            }
        }
    )
}