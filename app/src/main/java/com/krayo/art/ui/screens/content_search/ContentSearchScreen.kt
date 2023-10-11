package com.krayo.art.ui.screens.content_search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.R
import com.krayo.art.constants.Destinations
import com.krayo.art.ui.screens.content_search.components.SearchTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentSearchScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    updateNavState: (Boolean) -> Unit
) {
    //updateNavState(false)
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchTopBar(
                paddingValues = WindowInsets.statusBars.asPaddingValues(),
                navController = navController
            )
        },
    ) { paddingValues_ ->
        Column(
            modifier = Modifier
                .padding(paddingValues_)
                .padding(15.dp)
                .verticalScroll(rememberScrollState()),
        ) {
                Text(
                    "Recents",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    for (i in 0..2) {
                        SearchItem(
                            navController = navController,
                            data = SearchItemUIState(
                                name = "Contemporary Art",
                                leadingIcon = R.drawable.time_machine,
                                trailingIcon = R.drawable.close
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    "You may like",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    for (i in 0..10) {
                        SearchItem(
                            navController = navController,
                            data = SearchItemUIState(
                                name = "Banksy painting London",
                                leadingIcon = R.drawable.fire,
                            )
                        )
                    }
                }

        }
    }
}

data class SearchItemUIState(
    val name: String,
    val leadingIcon: Int,
    val trailingIcon: Int? = null
)

@Composable
fun SearchItem(
    navController: NavController,
    data: SearchItemUIState,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(
                    Destinations.SEARCH_RESULTS.name,
                )
            }
            .padding(top = 10.dp, bottom = 10.dp),
    ) {
        Icon(
            tint = Color.Gray,
            modifier = modifier
                .size(25.dp),
            painter = painterResource(id = data.leadingIcon),
            contentDescription = null
        )
        Spacer(Modifier.width(10.dp))
        Text(
            data.name,
            modifier = modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(Modifier.width(10.dp))

        if (data.trailingIcon != null) {
            Icon(
                tint = Color.Gray,
                modifier = modifier
                    .size(25.dp),
                painter = painterResource(id = data.trailingIcon),
                contentDescription = null
            )
        }

    }
}