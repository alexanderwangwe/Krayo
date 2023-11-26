package com.krayo.art.ui.screens.product_creation.subscreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.R
import com.krayo.art.constants.Destinations

enum class State {
    IMAGE, CREATION
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    navController: NavController, innerPadding: PaddingValues
) {
    Scaffold(topBar = {
        ProductCreationTopBar(
            navController = navController,
            modifier = Modifier,
            paddingValues = WindowInsets.statusBars.asPaddingValues()
        )
    }, bottomBar = {
        BottomBar(
            navController = navController, modifier = Modifier,
            paddingValues = WindowInsets.navigationBars.asPaddingValues()
        )
    }, containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
        ) {
            AddProductContent(
                modifier = Modifier.padding(horizontal = 15.dp),
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductCreationTopBar(
    navController: NavController, modifier: Modifier, paddingValues: PaddingValues
) {
    TopAppBar(title = {
        Box {
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
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.create_product),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    })
}

@Composable
private fun AttachImage(
    modifier: Modifier,
    navController: NavController
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 200.dp)
            .clip(
                RoundedCornerShape(
                    10.dp
                )
            ),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable {
                    navController.navigate(Destinations.CONTENT_CREATION.name)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.primary,
                modifier = modifier
                    .size(75.dp),
                painter = painterResource(id = R.drawable.baseline_add_photo_alternate),
                contentDescription = stringResource(id = R.string.attach_photo)
            )
            Spacer(modifier = modifier.padding(10.dp))
            Text(
                color = MaterialTheme.colorScheme.primary,
                text = stringResource(id = R.string.attach_photo),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTitle(
    modifier: Modifier
) {
    var title by rememberSaveable {
        mutableStateOf("")
    }

    TextField(
        singleLine = true,
        maxLines = 1,
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = TextFieldDefaults.textFieldColors(
            unfocusedLabelColor = Color.Gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor = MaterialTheme.colorScheme.background,
            textColor = MaterialTheme.colorScheme.onSurface,
        ),
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    10.dp
                )
            ), label = {
            Text(
                text = stringResource(id = R.string.product_title),
                style = MaterialTheme.typography.bodyMedium
            )
        }, value = title, onValueChange = {
            title = it
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddDescription(
    modifier: Modifier
) {
    var description by rememberSaveable {
        mutableStateOf("")
    }

    TextField(
        textStyle = MaterialTheme.typography.bodyMedium,
        maxLines = 15,
        colors = TextFieldDefaults.textFieldColors(
            unfocusedLabelColor = Color.Gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor = MaterialTheme.colorScheme.background,
            textColor = MaterialTheme.colorScheme.onSurface,
        ),
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 200.dp)
            .clip(
                RoundedCornerShape(
                    10.dp
                )
            ), label = {
            Text(
                text = stringResource(id = R.string.product_desc),
                style = MaterialTheme.typography.bodyMedium
            )
        }, value = description, onValueChange = {
            description = it
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddPrice(
    modifier: Modifier
) {
    var price by rememberSaveable {
        mutableStateOf("")
    }

    TextField(
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        leadingIcon = {
            Text(
                text = stringResource(id = R.string.currency),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedLabelColor = Color.Gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor = MaterialTheme.colorScheme.background,
            textColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    10.dp
                )
            ), label = {
            Text(
                text = stringResource(id = R.string.product_price),
                style = MaterialTheme.typography.bodyMedium
            )
        }, value = price, onValueChange = {
            price = it
        })
}

@Composable
private fun AddProductContent(
    navController: NavController,
    modifier: Modifier
) {
    Column {
        AttachImage(modifier = modifier, navController = navController)
        Spacer(modifier = modifier.padding(10.dp))
        AddTitle(modifier = modifier)
        Spacer(modifier = modifier.padding(10.dp))
        AddDescription(modifier = modifier)
        Spacer(modifier = modifier.padding(10.dp))
        Divider(
            color = MaterialTheme.colorScheme.background,
        )
        Spacer(modifier = modifier.padding(10.dp))
        AddPrice(modifier = modifier)
    }
}

@Composable
private fun BottomBar(
    paddingValues: PaddingValues,
    navController: NavController, modifier: Modifier
) {
    Button(modifier = modifier
        .fillMaxWidth()
        .padding(bottom = paddingValues.calculateBottomPadding() + 10.dp)
        .padding(horizontal = 15.dp)
        .height(50.dp), onClick = {
        navController.popBackStack()
    }) {
        Text(
            text = stringResource(id = R.string.add_product),
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
