package com.krayo.art.ui.screens.content_search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.R
import com.krayo.art.constants.Destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    defaultValue: String = "",
) {
    var searchKey by rememberSaveable {
        mutableStateOf(defaultValue)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(paddingValues).height(50.dp)
            .padding(horizontal = 15.dp),
    ) {
        Surface(
            modifier = modifier
                .clip(CircleShape)
                .height(35.dp)
                .clickable {
                    navController.popBackStack()
                },
            color = MaterialTheme.colorScheme.onBackground,
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = modifier
                    .padding(10.dp)
                    .size(15.dp),
                painter = painterResource(id = R.drawable.back),
                contentDescription = stringResource(id = R.string.go_back)
            )
        }
        Spacer(Modifier.width(10.dp))
        val interactionSource = remember { MutableInteractionSource() }
        BasicTextField(
            value = searchKey,
            onValueChange = { searchKey = it },
            visualTransformation = VisualTransformation.None,
            modifier = modifier
                .weight(1f)
                .height(35.dp)
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
                placeholder = {
                    Text(
                        "Contemporary Art",
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
                value = searchKey,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = true,
                enabled = true,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(horizontal = 15.dp, vertical = 0.dp),
            )
        }
        Spacer(Modifier.width(10.dp))
        Text(
            modifier = modifier.clickable {
                navController.navigate(Destinations.SEARCH_RESULTS.name)
            },
            text = "Search",
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
