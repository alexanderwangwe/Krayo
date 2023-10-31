package com.krayo.art.ui.screens.analytics

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.krayo.art.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(navController: NavController, paddingValues: PaddingValues) {
    val analyticsData = listOf(
        CardData(
            title = "Total Revenue",
            value = "$1,000",
            description = "Total revenue for the selected timeframe",
            comparison = 10,
            timeframe = Timeframe.WEEK
        ),
        CardData(
            title = "Total Revenue",
            value = "52",
            description = "Total revenue for the selected timeframe",
            comparison = 10,
            timeframe = Timeframe.WEEK
        ),
        CardData(
            title = "Total Revenue",
            value = "3890",
            description = "Total revenue for the selected timeframe",
            comparison = 10,
            timeframe = Timeframe.WEEK
        ),
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = { TopBar() },
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding() + 10.dp)
                .padding(horizontal = 15.dp),
            columns = GridCells.Fixed(2),
            content = {
            items(analyticsData.size) { index ->
                AnalyticsCard(data = analyticsData[index])
            }
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(navigationIcon = {
        Surface(
            color = MaterialTheme.colorScheme.background,
            shape = CircleShape,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                tint = Color.White,
                modifier = Modifier.padding(10.dp),
                painter = painterResource(id = R.drawable.back),
                contentDescription = null
            )
        }
    }, title = {
        Text("Analytics")
    },
        actions = {
            Surface(
                color = MaterialTheme.colorScheme.background,
                shape = CircleShape,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    Icons.Outlined.Menu,
                    tint = Color.White,
                    modifier = Modifier.padding(10.dp),
                    contentDescription = null
                )
            }
        })
}

enum class Timeframe {
    WEEK,
    MONTH,
    YEAR
}

data class CardData(
    val title: String,
    val value: String,
    val description: String,
    val comparison: Int,
    val timeframe: Timeframe
)

@Composable
fun AnalyticsCard(
    data: CardData,
){

    val timeframe = when (data.timeframe) {
        Timeframe.WEEK -> "Last 7 days"
        Timeframe.MONTH -> "Last 28 days"
        Timeframe.YEAR -> "Last 1 year"
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.padding(5.dp),
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        // Value for the card
        Text(text = data.value, fontSize = 32.sp, modifier = Modifier.padding(10.dp), style = MaterialTheme.typography.titleLarge)
        Text(text = data.title, modifier = Modifier.padding(horizontal = 10.dp), style = MaterialTheme.typography.bodyLarge)
        Text(text = timeframe, modifier = Modifier.padding(horizontal = 10.dp), style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(10.dp))
    }
}
