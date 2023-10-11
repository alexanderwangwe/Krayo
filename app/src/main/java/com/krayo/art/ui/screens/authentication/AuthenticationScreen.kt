package com.krayo.art.ui.screens.authentication

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.krayo.art.ui.screens.authentication.components.AuthTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    updateNavState: (Boolean) -> Unit
) {
    updateNavState(false)
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            AuthTopBar(
                navController = navController,
                paddingValues = paddingValues
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 15.dp, vertical = 50.dp)
        ) {
            InfoSection()
            AuthOptions(
                navController = navController,
            )
        }
    }
}

@Composable
private fun InfoSection(
    modifier: Modifier = Modifier,
) {
    Column {
        Text(
            text = stringResource(id = R.string.account_important),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
private fun AuthOptions(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Column(
        modifier = modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        RegisterAuthButton(modifier = modifier, navController = navController)
        Spacer(modifier = modifier.height(20.dp))
        Text(modifier = modifier.align(Alignment.CenterHorizontally), text = "Or")
        Spacer(modifier = modifier.height(20.dp))
        GoogleAuthButton(modifier = modifier, navController = navController)
        Spacer(modifier = modifier.height(10.dp))
        AppleAuthButton(modifier = modifier, navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterAuthButton(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var email by rememberSaveable {
        mutableStateOf("")
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
    ) {
        BasicTextField(
            value = email,
            onValueChange = { email = it },
            visualTransformation = VisualTransformation.None,
            modifier = modifier
                .weight(1f)
                .height(50.dp)
                .clip(RoundedCornerShape(50)),
            interactionSource = interactionSource,
            enabled = true,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                letterSpacing = MaterialTheme.typography.bodyLarge.letterSpacing,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight,
            ),
            singleLine = true,
        ) { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                leadingIcon = {
                    Icon(
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = modifier
                            .padding(15.dp)
                            .size(25.dp),
                        painter = painterResource(id = R.drawable.email),
                        contentDescription = stringResource(
                            id = R.string.go_back
                        ),
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.email_example),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    textColor = MaterialTheme.colorScheme.primary,
                ),
                value = email,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = true,
                enabled = true,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(horizontal = 15.dp, vertical = 0.dp),
            )
        }
    }

    Button(
        modifier = modifier
            .fillMaxWidth(),
        onClick = {
            navController.navigate(Destinations.EMAIL_VERIFICATION.name)
        }) {
        Text(
            text = stringResource(id = R.string.continue_text),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}

@Composable
private fun GoogleAuthButton(
    navController: NavController,
    modifier: Modifier
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.Black
        ),
        modifier = modifier
            .fillMaxWidth(),
        onClick = {
            navController.navigate(Destinations.EMAIL_VERIFICATION.name)
        }) {
        Row {
            Icon(
                painter = painterResource(
                    id =
                    R.drawable.google
                ), contentDescription = stringResource(id = R.string.auth_google)
            )
            Text(
                modifier = modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.auth_google),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }

    }
}

@Composable
private fun AppleAuthButton(
    navController: NavController,
    modifier: Modifier
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.Black
        ),
        modifier = modifier
            .fillMaxWidth(),
        onClick = {
            navController.navigate(Destinations.EMAIL_VERIFICATION.name)
        }) {
        Row {

            Icon(
                painter = painterResource(
                    id =
                    R.drawable.apple_logo
                ), contentDescription = stringResource(id = R.string.auth_apple)
            )
            Text(
                modifier = modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.auth_apple),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}
