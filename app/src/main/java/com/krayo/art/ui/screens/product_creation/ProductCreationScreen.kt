package com.krayo.art.ui.screens.product_creation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.R
import com.krayo.art.constants.Destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCreationScreen(
    navController: NavController,
    innerPadding: PaddingValues
) {
    Scaffold(
        topBar = {
            ProductCreationTopBar(
                navController = navController,
                modifier = Modifier,
                paddingValues = WindowInsets.statusBars.asPaddingValues()
            )
        },
        floatingActionButton = {
            AddProductFAB(navController = navController, modifier = Modifier)
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            ProductsListing(navController = navController)
        }
    }
}

@Composable
private fun AddProductFAB(
    navController: NavController,
    modifier: Modifier
) {
    Surface(
        modifier = modifier
            .padding(15.dp)
            .clickable {
                navController.navigate(Destinations.ADD_PRODUCT.name)
            }
            .size(50.dp),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            modifier = modifier.padding(10.dp),
            tint = MaterialTheme.colorScheme.onPrimary,
            painter = painterResource(id = R.drawable.plus_math),
            contentDescription = stringResource(id = R.string.add_product)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductCreationTopBar(
    navController: NavController,
    modifier: Modifier,
    paddingValues: PaddingValues
) {
    TopAppBar(modifier = modifier.padding(horizontal = 12.5.dp), navigationIcon = {
        Surface(
            modifier = Modifier
                .clip(CircleShape)
                .height(40.dp)
                .clickable {
                    navController.popBackStack()
                },
            color = MaterialTheme.colorScheme.background,
        ) {
            Icon(
                modifier = Modifier.padding(10.dp),
                tint = MaterialTheme.colorScheme.onSurface,
                painter = painterResource(id = R.drawable.back),
                contentDescription = stringResource(
                    id = R.string.go_back
                )
            )
        }
    }, title = {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.products),
            color = MaterialTheme.colorScheme.onSurface
        )
    },
        actions = {
            Surface(
                modifier = modifier
                    .size(40.dp),
                color = MaterialTheme.colorScheme.background,
                shape = CircleShape
            ) {
                Icon(
                    modifier = modifier
                        .padding(10.dp),
                    tint = MaterialTheme.colorScheme.onSurface,
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = stringResource(id = R.string.search)
                )
            }
        })
}

@Composable
private fun Product(
    navController: NavController
) {
    var isAttached by remember { mutableStateOf(false) }
    val attachText = if (isAttached) {
        stringResource(id = R.string.detach)
    } else {
        stringResource(id = R.string.attach)
    }
    val attachIcon = if (isAttached) {
        painterResource(id = R.drawable.icon_remove)
    } else {
        painterResource(id = R.drawable.plus_math)
    }
    val color = if (isAttached) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.primary
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, MaterialTheme.colorScheme.background)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Surface(
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxHeight(),
        ) {
            Icon(
                modifier = Modifier.padding(10.dp),
                tint = MaterialTheme.colorScheme.onSurface,
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(id = R.string.search)
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Column {
                Text(text = "Product Name")
                Text(text = "Product Description", style = MaterialTheme.typography.bodyMedium)
            }

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable {
                        isAttached = !isAttached
                        if(isAttached){
                            navController.popBackStack()
                        }
                    },
                verticalAlignment = Alignment.Bottom,
            ) {
                Icon(
                    tint = color,
                    painter = attachIcon,
                    contentDescription = attachText
                )
                Text(
                    text = attachText,
                    color = color,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun ProductsListing(
    navController: NavController
) {
    LazyVerticalGrid(columns = GridCells.Fixed(1), content = {
        items(10) {
            Product(navController = navController)
        }
    })
}
