package com.krayo.art.ui.screens.content_creation.subscreens

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.krayo.art.R
import com.krayo.art.ui.screens.content_creation.ContentCreationMode

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ContentScreen(
    navController: NavController,
    mode: ContentCreationMode,
    changeState: () -> Unit = {},
) {
    val cameraPermissionState: PermissionState =
        rememberPermissionState(android.Manifest.permission.CAMERA)
    val hasCameraPermission = cameraPermissionState.status.isGranted
    val permissionRefused = cameraPermissionState.status.shouldShowRationale

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (hasCameraPermission) {
                ContentCreationTopBar(
                    changeState = changeState,
                    navController = navController,
                    paddingValues = WindowInsets.statusBars.asPaddingValues()
                )
            }
        },
        bottomBar = {
            if (hasCameraPermission) {
                ContentCreationBottomBar(
                    mode = mode,
                    paddingValues = WindowInsets.navigationBars.asPaddingValues()
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) { paddingValues ->
        CameraContainer(
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            hasPermission = hasCameraPermission,
            permissionRefused = permissionRefused,
            onRequestPermission = { cameraPermissionState.launchPermissionRequest() }
        )
    }

}

@Composable
private fun ContentCreationTopBar(
    changeState: () -> Unit,
    navController: NavController,
    paddingValues: PaddingValues,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .padding(paddingValues)
            .padding(horizontal = 15.dp),
    ) {
        Icon(
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    if(changeState == {}){
                        changeState()
                        return@clickable
                    }
                    navController.popBackStack()
                },
            painter = painterResource(id = R.drawable.cancel), contentDescription = stringResource(
                id = R.string.go_back
            )
        )
        Button(colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
            contentColor = MaterialTheme.colorScheme.onSurface
        ), onClick = { /*TODO*/ }) {
            Text(
                text = stringResource(id = R.string.add_sounds),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 15.sp
            )
        }
        Icon(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = R.drawable.synchronize),
            contentDescription = stringResource(
                id = R.string.go_back
            )
        )
    }
}

@Composable
private fun ContentCreationBottomBar(
    mode: ContentCreationMode,
    paddingValues: PaddingValues
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.round_layers_24),
                contentDescription = stringResource(
                    id = R.string.effects
                )
            )
            Text(
                text = stringResource(id = R.string.effects),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 15.sp
            )
        }

        CaptureButton(
            modifier = Modifier.padding(horizontal = 25.dp),
            mode = mode,
        )

        Column(
            modifier = Modifier.padding(horizontal = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Surface(
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(35.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                // TODO: Add latest image here
            }
            Text(
                text = stringResource(id = R.string.upload),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
private fun CameraContainer(
    navController: NavController,
    modifier: Modifier,
    hasPermission: Boolean,
    permissionRefused: Boolean,
    onRequestPermission: () -> Unit = {}
) {

    if (hasPermission) {
        CameraPreview(
            modifier = modifier,
            backgroundColor = MaterialTheme.colorScheme.surface.toArgb()
        )
    } else {
        PermissionRequest(
            navController = navController,
            permissionRefused = permissionRefused,
            modifier = modifier,
            onRequestPermission = onRequestPermission
        )
    }
}

@Composable
private fun CameraPreview(
    backgroundColor: Int,
    modifier: Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember {
        LifecycleCameraController(context)
    }

    AndroidView(factory = { context ->
        PreviewView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                MATCH_PARENT,
                MATCH_PARENT
            )
            setBackgroundColor(backgroundColor)
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }.also {
            it.controller = cameraController
            cameraController.bindToLifecycle(lifecycleOwner)

        }
    }, modifier = modifier)
}

@Composable
private fun CaptureButton(
    mode: ContentCreationMode,
    modifier: Modifier,
    onClick: () -> Unit = {}
) {

    val color = when (mode) {
        ContentCreationMode.VIDEO -> Color.Red
        ContentCreationMode.PHOTO -> Color.White
        else -> Color.White
    }
    Box(
        modifier = modifier
            .size(80.dp)
            .clickable {

            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent, shape = CircleShape)
                .padding(4.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = Color.Transparent,
                    radius = size.minDimension / 2,
                    center = center
                )

                val outerCircleRadius = size.minDimension / 2 - 2.dp.toPx()
                val innerCircleRadius = size.minDimension / 2 - 7.dp.toPx()

                drawCircle(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.White, Color.White),
                        start = center.copy(y = 0f),
                        end = center
                    ),
                    style = Stroke(3.dp.toPx()),
                    radius = outerCircleRadius,
                    center = center
                )

                drawCircle(
                    color = color,
                    radius = innerCircleRadius,
                    center = center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PermissionRequest(
    navController: NavController,
    modifier: Modifier,
    permissionRefused: Boolean,
    onRequestPermission: () -> Unit = {}
) {
    val title = if (permissionRefused) {
        "Permission denied"
    } else {
        "Permission required"
    }

    val message = if (permissionRefused) {
        stringResource(id = R.string.permission_denied)
    } else {
        stringResource(id = R.string.permission_grant)
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        WindowInsets.statusBars.asPaddingValues()
                    ),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            navController.popBackStack()
                        },
                    painter = painterResource(id = R.drawable.cancel),
                    contentDescription = stringResource(
                        id = R.string.go_back
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Button(
                onClick = onRequestPermission,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.grant_permission),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

