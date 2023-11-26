package com.krayo.art.ui.screens.content_creation.subscreens

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.MainActivity
import com.krayo.art.R
import com.krayo.art.constants.Destinations
import com.krayo.art.ui.components.content_display.VideoPlayer
import kotlinx.coroutines.delay

fun isVideoReadable(filePath: String): Boolean {
    return try {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(filePath)
        true
    } catch (e: Exception) {
        Log.d("VideoFileObserver", "Video file not readable: $e")
        false
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LastCapturedContent(
    navController: NavController,
    activity: MainActivity
) {
    val sharedPref = activity.getSharedPreferences("videoField", Context.MODE_PRIVATE)
    val videoFile = sharedPref.getString("videoField", "")
    val lastCapturedVideo by remember {
        mutableStateOf(Uri.parse(videoFile))
    }
    var videoIsReadable by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(lastCapturedVideo) {
        delay(1000)
        do{
            delay(1000) // Wait for 1 second
            if (isVideoReadable(lastCapturedVideo.path!!)) {
                videoIsReadable = true
                break
            }
        }
        while (true)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.padding(top = 10.dp),
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateBottomPadding()
                    )
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = Color.Black
                    ), onClick = {
                        navController.popBackStack()
                    }, modifier = Modifier
                        .weight(1f)
                        .padding(end = 7.5.dp)
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.onSurface,
                        text = stringResource(id = R.string.discard),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Button(
                    onClick = {
                        navController.navigate(Destinations.CONTENT_CREATION_OPTIONS.name)
                    }, modifier = Modifier
                        .weight(1f)
                        .padding(start = 7.5.dp)
                ) {
                    Text(
                        color = Color.Black,
                        text = stringResource(id = R.string.next),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    ) { paddingValues ->
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background, contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(bottom = 15.dp)
                .padding(horizontal = 5.dp),
            shape = MaterialTheme.shapes.large
        ) {
            if (videoIsReadable)
                VideoPlayer(
                    uri = lastCapturedVideo,
                    modifier = Modifier.padding(15.dp),
                    playWhenReadyParent = true,
                    playedFromNonHomeOrDiscover = true
                )
        }
    }
}
