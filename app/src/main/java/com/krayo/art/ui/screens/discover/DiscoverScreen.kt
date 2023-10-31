package com.krayo.art.ui.screens.discover

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
            Column(
                modifier = Modifier
                    .padding(
                        top = WindowInsets.statusBars
                            .asPaddingValues()
                            .calculateTopPadding() + 10.dp
                    )
            ){
                Row(
                    modifier = Modifier
                        .padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                )
                {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
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
                    Icon(
                        Icons.Outlined.Search,
                        modifier = Modifier.padding(10.dp),
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null
                    )
                }
                Categories(items = arrayListOf("For you", "Art", "Photography", "Music", "Dance", "Theatre", "Comedy", "Film", "Literature"))
            }
        },
    ) { paddingValues_ ->
        TrendingGrid(paddingValues_)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Categories(
    items: ArrayList<String>
){
    var selected by remember { mutableStateOf(items[0]) }

    LazyRow{
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }
        items(items.size){ index ->
            FilterChip(
                colors = FilterChipDefaults.filterChipColors(
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    containerColor = MaterialTheme.colorScheme.surface,
                    disabledSelectedContainerColor = MaterialTheme.colorScheme.surface,
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier.padding(horizontal = 5.dp),
                onClick = { selected = items[index] },
                label = {
                    Text(items[index], color = if(selected == items[index]) Color.Black else MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodyMedium)
                },
                selected = selected == items[index],
                leadingIcon = if (selected == items[index]) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
            )
        }
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }
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
            .padding(top = paddingValues.calculateTopPadding() + 10.dp)
            .padding(horizontal = 15.dp),
        columns = GridCells.Fixed(2),
        content = {
            items(count = 51) { i ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(0.6f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.onBackground)
                )
            }
        }
    )
}
