package com.krayo.art.ui.screens.authentication.subscreens

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.krayo.art.R
import com.krayo.art.constants.Destinations
import com.krayo.art.ui.screens.authentication.components.AuthTopBar
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailVerification(
    navController: NavController,
    paddingValues: PaddingValues,
) {
    var fullPin by rememberSaveable { mutableStateOf("") }

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
            Spacer(modifier = Modifier.height(50.dp))
            PinLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                text = "",
                length = 4,
                keyboardType = KeyboardType.Number,
                onTextChange = { pin ->
                    fullPin = pin
                    Log.d("PINCHECK", pin)
                    if (pin.length == 4) navController.navigate(Destinations.AUTH_SUCCESS.name)
                }
            )
            Spacer(modifier = Modifier.height(25.dp))
            ResendEmail()
        }
    }
}

@Composable
private fun InfoSection(
    modifier: Modifier = Modifier,
) {
    Column {
        Text(
            text = stringResource(id = R.string.email_verification),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Field(
    index: Int,
    modifier: Modifier = Modifier,
    onValueChange: (String, String) -> String = { _, new -> new },
    nextFocusRequester: FocusRequester,
    focusRequester: FocusRequester,
    previousFocus: FocusRequester,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val state = rememberSaveable { mutableStateOf("") }

    BasicTextField(
        value = state.value,
        onValueChange = {
            val value = onValueChange(state.value, it)
            state.value = value
        },
        keyboardActions = KeyboardActions(
            onDone = { focusRequester.requestFocus() }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        visualTransformation = VisualTransformation.None,
        modifier = modifier
            .focusRequester(focusRequester)
            .onKeyEvent { event: KeyEvent ->
                if (event.type == KeyEventType.KeyUp &&
                    event.key == Key.Backspace
                ) {
                    focusRequester.freeFocus()
                    previousFocus.requestFocus()
                } else {
                    focusRequester.freeFocus()
                    nextFocusRequester.requestFocus()
                }
                true
            }
            .height(75.dp),
        interactionSource = interactionSource,
        enabled = true,
        textStyle = TextStyle(
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp,
            fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
            letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
            lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
        ),
        singleLine = true,
    ) { innerTextField ->
        TextFieldDefaults.TextFieldDecorationBox(
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = MaterialTheme.colorScheme.onBackground,
                textColor = MaterialTheme.colorScheme.primary,
            ),
            value = state.value,
            visualTransformation = VisualTransformation.None,
            innerTextField = innerTextField,
            singleLine = true,
            enabled = true,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(horizontal = 15.dp, vertical = 0.dp),
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PinLayout(
    modifier: Modifier = Modifier,
    text: String = "",
    length: Int = 4,
    keyboardType: KeyboardType = KeyboardType.Number,
    onTextChange: (String) -> Unit
) {
    var char1 by rememberSaveable {
        mutableStateOf("")
    }
    var char2 by rememberSaveable {
        mutableStateOf("")
    }
    var char3 by rememberSaveable {
        mutableStateOf("")
    }
    var char4 by rememberSaveable {
        mutableStateOf("")
    }
    val listOfFocusRequesters = arrayListOf(
        FocusRequester(),
        FocusRequester(),
        FocusRequester(),
        FocusRequester(),
    )

    if (char1.isNotEmpty() && char2.isNotEmpty() && char3.isNotEmpty() && char4.isNotEmpty()) {
        onTextChange(char1 + char2 + char3 + char4)
    }

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(length) { index ->
            Field(
                index = index,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp),
                onValueChange = { old, new ->
                    when(index){
                        0 -> char1 = new
                        1 -> char2 = new
                        2 -> char3 = new
                        3 -> char4 = new
                    }
                    if (new.length > 1 || new.any { !it.isDigit() }) old else new
                },
                focusRequester = listOfFocusRequesters[index],
                nextFocusRequester = listOfFocusRequesters.getOrNull(index + 1)
                    ?: listOfFocusRequesters[length - 1],
                previousFocus = listOfFocusRequesters.getOrNull(index - 1)
                    ?: listOfFocusRequesters[0],
            )
        }
    }
}

@Composable
private fun ResendEmail() {
    var timeLeft by rememberSaveable { mutableIntStateOf(45) }

    LaunchedEffect(key1 = timeLeft) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
    }

    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = stringResource(id = R.string.resend_email, timeLeft),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.error
    )
}