package com.krayo.art.ui.screens.content_creation.subscreens

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.LensFacing
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.krayo.art.R
import com.krayo.art.constants.Destinations
import com.krayo.art.ui.screens.content_creation.ContentCreationMode
import com.krayo.art.ui.theme.DeepRed
import com.krayo.art.ui.theme.LightText
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ContentScreen(
    outputDirectory: File,
    navController: NavController,
    mode: ContentCreationMode,
    updateBottomBarState: (Boolean) -> Unit,
    changeState: () -> Unit = {},
) {
    val cameraPermissionState: PermissionState =
        rememberPermissionState(android.Manifest.permission.CAMERA)
    val hasCameraPermission = cameraPermissionState.status.isGranted
    val permissionRefused = cameraPermissionState.status.shouldShowRationale
    val capturedContent = rememberSaveable {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    val hasPhotoCaptured = capturedContent.value != Uri.EMPTY
    if (hasPhotoCaptured) {
        updateBottomBarState(false)
    } else {
        updateBottomBarState(true)
    }

    var lensFacingState by rememberSaveable {
        mutableStateOf(CameraFacing.BACK)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (hasCameraPermission && !hasPhotoCaptured) {
                ContentCreationTopBar(
                    onCameraFlip = {
                        lensFacingState = if(lensFacingState == CameraFacing.BACK) {
                            CameraFacing.FRONT
                        } else {
                            CameraFacing.BACK
                        }
                    },
                    changeState = changeState,
                    navController = navController,
                    paddingValues = WindowInsets.statusBars.asPaddingValues()
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) { paddingValues ->
        CameraContainer(
            lensFacingState = lensFacingState,
            mode = mode,
            outputDirectory = outputDirectory,
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            hasPermission = hasCameraPermission,
            permissionRefused = permissionRefused,
            onRequestPermission = { cameraPermissionState.launchPermissionRequest() },
            lifecycleOwner = lifecycleOwner,
            updateContentCapturedState = {
                capturedContent.value = it
            }
        )
    }

}

@Composable
private fun ContentCreationTopBar(
    onCameraFlip: () -> Unit,
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
                    if (changeState == {}) {
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
            modifier = Modifier.size(30.dp).clickable {
                onCameraFlip()
            },
            painter = painterResource(id = R.drawable.synchronize),
            contentDescription = stringResource(
                id = R.string.go_back
            )
        )
    }
}

@Composable
private fun ContentCreationBottomBar(
    outputDirectory: File,
    executor: Executor,
    imageCapture: ImageCapture,
    mode: ContentCreationMode,
    paddingValues: PaddingValues,
    onCapture: (Uri) -> Unit
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
            executor = executor,
            imageCapture = imageCapture,
            outputDirectory = outputDirectory,
            modifier = Modifier.padding(horizontal = 25.dp),
            mode = mode,
        ) {
            onCapture(it)
        }

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
    lensFacingState: CameraFacing,
    mode: ContentCreationMode,
    outputDirectory: File,
    navController: NavController,
    modifier: Modifier,
    hasPermission: Boolean,
    permissionRefused: Boolean,
    onRequestPermission: () -> Unit = {},
    updateContentCapturedState: (Uri) -> Unit = {},
    lifecycleOwner: LifecycleOwner
) {
    var contentCaptured by rememberSaveable {
        mutableStateOf(false)
    }
    var capturedPhoto by rememberSaveable {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    if (hasPermission) {
        if (contentCaptured) {
            LastPhotoPreview(
                modifier = modifier,
                lastCapturedPhoto = capturedPhoto,
                onDiscardPhoto = {
                    contentCaptured = false
                    capturedPhoto = Uri.EMPTY
                    updateContentCapturedState(Uri.EMPTY)
                },
                onProceedButtonClick = {
                    navController.navigate(Destinations.CONTENT_CREATION_OPTIONS.name)
                }
            )
        } else {
            CameraPreview(
                lensFacingMode = lensFacingState,
                outputDirectory = outputDirectory,
                mode = mode,
                lifecycleOwner = lifecycleOwner,
                onContentCapture = {
                    contentCaptured = true
                    capturedPhoto = it
                    updateContentCapturedState(it)
                }
            )
        }
    } else {
        PermissionRequest(
            navController = navController,
            permissionRefused = permissionRefused,
            modifier = modifier,
            onRequestPermission = onRequestPermission
        )
    }
}

enum class CameraFacing {
    FRONT,
    BACK
}

@Composable
private fun CameraPreview(
    outputDirectory: File,
    mode: ContentCreationMode,
    onContentCapture: (Uri) -> Unit = {},
    lifecycleOwner: LifecycleOwner,
    lensFacingMode: CameraFacing,
) {
    val lensFacing = when (lensFacingMode) {
        CameraFacing.FRONT -> CameraSelector.LENS_FACING_FRONT
        CameraFacing.BACK -> CameraSelector.LENS_FACING_BACK
    }
    val context = LocalContext.current

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()
    val executor = Executors.newSingleThreadExecutor()

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        ContentCreationBottomBar(
            executor = executor,
            imageCapture = imageCapture,
            outputDirectory = outputDirectory,
            mode = mode,
            paddingValues = WindowInsets.navigationBars.asPaddingValues()
        ) {
            onContentCapture(it)
        }
    }
}

private fun takePhoto(
    filenameFormat: String,
    imageCapture: ImageCapture,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {

    val photoFile = File(
        outputDirectory,
        "KRAYO - " + SimpleDateFormat(
            filenameFormat,
            Locale.US
        ).format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
        override fun onError(exception: ImageCaptureException) {
            Log.e("CapturePhoto", "Take photo error:", exception)
            onError(exception)
        }

        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val savedUri = Uri.fromFile(photoFile)
            Log.d("CapturePhoto", "Photo capture succeeded: $savedUri")
            onImageCaptured(savedUri)
        }
    })
}


private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }

@Composable
private fun CaptureButton(
    outputDirectory: File,
    executor: Executor,
    imageCapture: ImageCapture,
    mode: ContentCreationMode,
    modifier: Modifier,
    onClick: (Uri) -> Unit = {}
) {
    val color = when (mode) {
        ContentCreationMode.VIDEO -> DeepRed
        ContentCreationMode.PHOTO -> Color.White
        else -> Color.White
    }
    Box(
        modifier = modifier
            .size(80.dp)
            .clickable {
                when (mode) {
                    ContentCreationMode.VIDEO -> {

                    }

                    ContentCreationMode.PHOTO -> {
                        takePhoto(
                            filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                            imageCapture = imageCapture,
                            outputDirectory = outputDirectory,
                            executor = executor,
                            onImageCaptured = { uri ->
                                onClick(uri)
                            },
                            onError = { exception ->
                                Log.e("CapturePhoto", "Take photo error:", exception)
                            }
                        )
                    }

                    else -> {}
                }
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
private fun LastPhotoPreview(
    modifier: Modifier,
    lastCapturedPhoto: Uri,
    onDiscardPhoto: () -> Unit,
    onProceedButtonClick: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.padding(horizontal = 15.dp),
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    onClick = {
                        onDiscardPhoto()
                    },
                    modifier = Modifier.weight(1f).padding(end = 7.5.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.discard),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Button(
                    onClick = {
                        onProceedButtonClick()
                    },
                    modifier = Modifier.weight(1f).padding(start = 7.5.dp)
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
            modifier = modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(bottom = 15.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Image(
                modifier = modifier
                    .fillMaxSize(),
                painter = rememberImagePainter(lastCapturedPhoto),
                contentDescription = "Last captured photo",
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
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

