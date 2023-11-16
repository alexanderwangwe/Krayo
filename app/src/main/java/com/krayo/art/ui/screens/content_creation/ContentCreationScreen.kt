package com.krayo.art.ui.screens.content_creation

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.MainActivity
import com.krayo.art.ui.screens.content_creation.subscreens.ContentScreen
import com.krayo.art.ui.theme.Grey60
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentCreationScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    context: MainActivity,
    updateNavState: (Boolean) -> Unit,
    getOutputDirectory: () -> File,
    ) {
    val outputDirectory = getOutputDirectory()
    var currentMode by rememberSaveable {
        mutableStateOf(ContentCreationMode.VIDEO)
    }
    updateNavState(false)
    var showBottomBar by rememberSaveable {
        mutableStateOf(true)
    }

    val window = (context as Activity).window
    window.statusBarColor = Color.Transparent.toArgb()
    window.navigationBarColor = Color.Black.toArgb()
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color.Black,
        bottomBar = {
            if(showBottomBar){
                ContentCreationBottomBar(
                    paddingValues = WindowInsets.navigationBars.asPaddingValues()
                ) { mode: ContentCreationMode ->
                    currentMode = mode
                }
            }
        },
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(bottom = paddingValues.calculateBottomPadding())
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            ContentScreen(
                outputDirectory = outputDirectory,
                navController = navController,
                mode = currentMode,
                updateBottomBarState = {
                    showBottomBar = it
                },
                activity = context
            )

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

    val explainerBar = listOf(
        "Showcase your product",
        "Explain your product",
        "Show your product in action",
        "Sell your product",
    )

    val rangeLimit = explainerBar.size - 1
    val randomNumber = (0..rangeLimit).random()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier.padding(vertical = 10.dp)
        ){
            Text(explainerBar[randomNumber], color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
        }
        Divider(
            thickness = 1.dp,
                color = Grey60
        )
        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .clickable {
                    currentMode = ContentCreationMode.VIDEO
                    modeChangedListener(currentMode)
                },
            style = MaterialTheme.typography.bodyLarge,
            text = "Video",
            color = if (currentMode == ContentCreationMode.VIDEO) MaterialTheme.colorScheme.primary else Color.Gray,
        )
    }
}
