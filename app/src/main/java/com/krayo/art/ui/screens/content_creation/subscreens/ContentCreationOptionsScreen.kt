package com.krayo.art.ui.screens.content_creation.subscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.R
import com.krayo.art.constants.Destinations


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentCreationOptionsScreen(
    navController: NavController,
    innerPadding: PaddingValues,
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
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
                        text = stringResource(id = R.string.post),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            })
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()),
                onClick = {
                    navController.navigate(Destinations.HOME.name)
                }) {
                Text(
                    stringResource(id = R.string.finish),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }) { paddingValues ->
        Column(
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .height(150.dp),
            ) {

                ContentDescription(
                    modifier = Modifier.weight(3f)
                )
                ContentCover(
                    modifier = Modifier.width(100.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Divider(
                color = MaterialTheme.colorScheme.background,
            )
            AttachProduct(navController = navController)
        }
    }
}

@Composable
private fun ContentCover(
    modifier: Modifier
) {
    Surface(
        modifier = modifier.fillMaxHeight(), color = Color.Gray
    ) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContentDescription(
    modifier: Modifier
) {
    var description by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.background,
            )
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        BasicTextField(
            value = description,
            onValueChange = { description = it },
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            enabled = true,
            textStyle = TextStyle(
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
            ),
            singleLine = false,
        ) { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                placeholder = {
                    Text(
                        textAlign = TextAlign.Start,
                        text = "Describe your content in greater detail, add hashtags, or mention people",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.primary,
                ),
                value = description,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = true,
                enabled = true,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(horizontal = 15.dp, vertical = 0.dp),
            )
        }
    }
}

@Composable
private fun Product() {
    val attachText = stringResource(id = R.string.detach)
    val attachIcon = painterResource(id = R.drawable.icon_remove)
    val color = MaterialTheme.colorScheme.tertiary
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, MaterialTheme.colorScheme.background)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Surface(
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp)),
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
                .padding(10.dp)
        ) {
            Column {
                Text(text = "Product Name")
                Text(text = "Product Description", style = MaterialTheme.typography.bodyMedium)
            }

            Row(
                modifier = Modifier
                    .clickable {
                        // TODO: Add detach functionality
                    },
                verticalAlignment = Alignment.Bottom,
            ) {
                Icon(
                    tint = color, painter = attachIcon, contentDescription = attachText
                )
                Text(
                    text = attachText, color = color, style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun AttachProduct(
    navController: NavController
) {
    Column {
        Product()
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            onClick = {
                navController.navigate(Destinations.PRODUCT_CREATION.name)
            }) {
            Text(
                stringResource(id = R.string.attach_product),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
