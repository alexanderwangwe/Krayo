package com.krayo.art.ui.screens.content_creation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.ui.screens.content_creation.subscreens.ContentScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentCreationScreen(
    updateNavState: (Boolean) -> Unit,
    navController: NavController, paddingValues: PaddingValues
) {
    var currentMode by rememberSaveable {
        mutableStateOf(ContentCreationMode.VIDEO)
    }
    updateNavState(false)
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            ContentCreationBottomBar(
                paddingValues = WindowInsets.navigationBars.asPaddingValues()
            ) { mode: ContentCreationMode ->
                currentMode = mode
            }
        },
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(bottom = paddingValues.calculateBottomPadding())
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            when (currentMode) {
                ContentCreationMode.UPLOAD -> {
                    // TODO: Implement upload screen
                }

                ContentCreationMode.VIDEO -> {
                    ContentScreen(
                        navController = navController,
                        mode = currentMode
                    )
                }

                ContentCreationMode.PHOTO -> {
                    ContentScreen(
                        navController = navController,
                        mode = currentMode
                    )
                }
            }

        }
    }
}

enum class ContentCreationMode {
    UPLOAD, VIDEO, PHOTO
}

@Composable
private fun ContentCreationBottomBar(
    paddingValues: PaddingValues,
    modeChangedListener: (ContentCreationMode) -> Unit = {}
) {
    var currentMode by rememberSaveable {
        mutableStateOf(ContentCreationMode.VIDEO)
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .padding(paddingValues),
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .clickable {
                    currentMode = ContentCreationMode.UPLOAD
                    modeChangedListener(currentMode)
                },
            style = MaterialTheme.typography.bodyLarge,
            text = "Upload",
            color = if (currentMode == ContentCreationMode.UPLOAD) MaterialTheme.colorScheme.tertiary else Color.Gray,
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .clickable {
                    currentMode = ContentCreationMode.VIDEO
                    modeChangedListener(currentMode)
                },
            style = MaterialTheme.typography.bodyLarge,
            text = "Video",
            color = if (currentMode == ContentCreationMode.VIDEO) MaterialTheme.colorScheme.primary else Color.Gray,
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .clickable {
                    currentMode = ContentCreationMode.PHOTO
                    modeChangedListener(currentMode)
                },
            style = MaterialTheme.typography.bodyLarge,
            text = "Photo",
            color = if (currentMode == ContentCreationMode.PHOTO) MaterialTheme.colorScheme.secondary else Color.Gray,
        )
    }
}
