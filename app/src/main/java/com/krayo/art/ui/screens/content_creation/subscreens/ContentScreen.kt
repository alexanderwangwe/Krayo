package com.krayo.art.ui.screens.content_creation.subscreens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Consumer
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.krayo.art.MainActivity
import com.krayo.art.R
import com.krayo.art.constants.Destinations
import com.krayo.art.ui.screens.content_creation.ContentCreationMode
import com.krayo.art.ui.theme.DeepRed
import com.krayo.art.ui.theme.Grey60
import kotlinx.coroutines.delay
import java.io.File
import java.lang.reflect.UndeclaredThrowableException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ContentScreen(
    outputDirectory: File,
    navController: NavController,
    mode: ContentCreationMode,
    updateBottomBarState: (Boolean) -> Unit,
    changeState: () -> Unit = {},
    activity: MainActivity
) {
    val cameraPermissionState: PermissionState =
        rememberPermissionState(android.Manifest.permission.CAMERA)
    val audioPermissionState: PermissionState =
        rememberPermissionState(android.Manifest.permission.RECORD_AUDIO)
    val videoPermissionState: PermissionState =
        rememberPermissionState(android.Manifest.permission.READ_MEDIA_VIDEO)
    val hasCameraPermission = cameraPermissionState.status.isGranted
    val hasAudioPermission = audioPermissionState.status.isGranted
    val hasVideoPermission = videoPermissionState.status.isGranted
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
            if (hasCameraPermission && hasAudioPermission && !hasPhotoCaptured) {
                ContentCreationTopBar(
                    onCameraFlip = {
                        lensFacingState = if (lensFacingState == CameraFacing.BACK) {
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
            hasVideoPermission = hasVideoPermission,
            hasAudioPermission = hasAudioPermission,
            hasPermission = hasCameraPermission,
            permissionRefused = permissionRefused,
            onRequestPermission = {
                cameraPermissionState.launchPermissionRequest()
                audioPermissionState.launchPermissionRequest()
                videoPermissionState.launchPermissionRequest()
            },
            lifecycleOwner = lifecycleOwner,
            updateContentCapturedState = {
                capturedContent.value = it
            },
            activity = activity
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
            .padding(horizontal = 10.dp),
    ) {
        IconButton(onClick = {
            if (changeState == {}) {
                changeState()
                return@IconButton
            }
            navController.popBackStack(Destinations.ONBOARDING_PROCESS.name, false)
        }) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = Grey60.copy(alpha = 0.5f)
            ) {
                Icon(
                    tint = Color.White,
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(id = R.drawable.cancel),
                    contentDescription = stringResource(
                        id = R.string.go_back
                    )
                )
            }
        }
        Button(colors = ButtonDefaults.buttonColors(
            containerColor = Grey60.copy(alpha = 0.5f), contentColor = Color.White
        ), onClick = { /*TODO*/ }) {
            Text(
                text = stringResource(id = R.string.add_sounds),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        IconButton(onClick = {
            onCameraFlip()
        }) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = Grey60.copy(alpha = 0.5f)
            ) {
                Icon(
                    tint = Color.White,
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(id = R.drawable.synchronize),
                    contentDescription = stringResource(
                        id = R.string.go_back
                    )
                )
            }
        }
    }
}

@Composable
private fun ContentCreationBottomBar(
    outputDirectory: File,
    mode: ContentCreationMode,
    videoPlaybackTime: Long,
    videoCapture: () -> Unit,
    onResumeVideoRecording: () -> Unit,
    onPauseVideoRecording: () -> Unit,
    onStopVideoRecording: () -> Unit,
    currentRecordingState: MutableState<RecordingState>,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        if (currentRecordingState.value == RecordingState.PAUSED) Column(
            modifier = Modifier.padding(horizontal = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                tint = Color.White,
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                contentDescription = stringResource(
                    id = R.string.effects
                )
            )
            Text(
                text = stringResource(id = R.string.discard),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp
            )
        }

        CaptureButton(modifier = Modifier.padding(horizontal = 25.dp),
            mode = mode,
            videoPlaybackTime = videoPlaybackTime,
            currentRecordingState = currentRecordingState,
            onPauseVideoRecording = {
                onPauseVideoRecording()
            },
            onResumeVideoRecording = {
                onResumeVideoRecording()
            },
            videoCapture = {
                videoCapture()
            })

        if (currentRecordingState.value == RecordingState.PAUSED) Column(
            modifier = Modifier.padding(horizontal = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            IconButton(onClick = {
                onStopVideoRecording()
            }) {
                Icon(
                    tint = Color.White,
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.baseline_check_circle_24),
                    contentDescription = stringResource(
                        id = R.string.effects
                    )
                )
            }
            Text(
                text = stringResource(id = R.string.next),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp
            )
        }

    }
}

private fun onVideoSave(
    navController: NavController,
    activity: MainActivity,
    videoUri: Uri
) {
    activity.runOnUiThread {
        val sharedPref = activity.getSharedPreferences("videoField", Context.MODE_PRIVATE)

        with(sharedPref.edit()) {
            putString("videoField", videoUri.toString())
            apply()
        }.let {
            navController.navigate(Destinations.LAST_CAPTURED_CONTENT.name)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
private fun CameraContainer(
    lensFacingState: CameraFacing,
    mode: ContentCreationMode,
    outputDirectory: File,
    navController: NavController,
    modifier: Modifier,
    hasVideoPermission: Boolean,
    hasAudioPermission: Boolean,
    hasPermission: Boolean,
    permissionRefused: Boolean,
    onRequestPermission: () -> Unit = {},
    updateContentCapturedState: (Uri) -> Unit = {},
    lifecycleOwner: LifecycleOwner,
    activity: MainActivity
) {

    if (hasPermission && hasAudioPermission) {
        CameraPreview(lensFacingMode = lensFacingState,
            outputDirectory = outputDirectory,
            mode = mode,
            onContentCapture = { videoUri, videoFile ->
                onVideoSave(
                    navController = navController,
                    activity = activity,
                    videoUri = Uri.fromFile(videoFile)
                )
            })
    } else {
        PermissionRequest(
            navController = navController,
            permissionRefused = permissionRefused,
            modifier = modifier,
            hasVideoPermission = hasVideoPermission,
            hasAudioPermission = hasAudioPermission,
            hasCameraPermission = hasPermission,
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
    onContentCapture: (Uri, File) -> Unit = { _: Uri, _: File -> },
    lensFacingMode: CameraFacing,
) {
    val lensFacing = when (lensFacingMode) {
        CameraFacing.FRONT -> CameraSelector.LENS_FACING_FRONT
        CameraFacing.BACK -> CameraSelector.LENS_FACING_BACK
    }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var recording: Recording? = remember { null }
    var recordingStopped: Boolean by remember { mutableStateOf(false) }
    val previewView: PreviewView = remember { PreviewView(context) }
    val preview =
        Preview.Builder().build().apply { setSurfaceProvider(previewView.surfaceProvider) }
    val qualitySelector = QualitySelector.from(
        Quality.FHD, FallbackStrategy.lowerQualityOrHigherThan(Quality.FHD)
    )
    val executor = Executors.newSingleThreadExecutor()
    val recorder =
        Recorder.Builder().setExecutor(executor).setQualitySelector(qualitySelector).build()
    val videoCaptureRaw = VideoCapture.withOutput(recorder)
    val recordingStarted: MutableState<Boolean> = remember { mutableStateOf(false) }
    val recordingPaused: MutableState<Boolean> = remember { mutableStateOf(true) }
    val audioEnabled: MutableState<Boolean> = remember { mutableStateOf(true) }
    val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val currentRecordingState: MutableState<RecordingState> =
        remember { mutableStateOf(RecordingState.NOT_STARTED) }
    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner, cameraSelector, preview, videoCaptureRaw
        )
    }

    val videoCapture = remember { mutableStateOf<VideoCapture<Recorder>?>(videoCaptureRaw) }
    var videoPlaybackTime by remember {
        mutableLongStateOf(0L)
    }
    var videoUri by remember {
        mutableStateOf(Uri.EMPTY)
    }
    var videoFile by remember {
        mutableStateOf(File(""))
    }

    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        ContentCreationBottomBar(outputDirectory = outputDirectory,
            mode = mode,
            videoPlaybackTime = videoPlaybackTime,
            currentRecordingState = currentRecordingState,
            onStopVideoRecording = {
                recording?.stop()
                recording?.close()
                recording = null

                currentRecordingState.value = RecordingState.STOPPED
                recordingStarted.value = false
                recordingPaused.value = true
                videoPlaybackTime = 0L
                recordingStopped = true
                onContentCapture(videoUri, videoFile)
            },
            onResumeVideoRecording = {
                recordingPaused.value = false
                recording?.resume()
                currentRecordingState.value = RecordingState.RECORDING
                recordingStarted.value = true
            },
            onPauseVideoRecording = {
                recordingPaused.value = true
                recording?.pause()
                currentRecordingState.value = RecordingState.PAUSED
                recordingStarted.value = false
                // TODO: stop video and start a new recording
            },
            videoCapture = {
                try {
                    videoCapture.value?.let { videoCapture ->
                        recordingStarted.value = true
                        currentRecordingState.value = RecordingState.RECORDING
                        val mediaDir = context.externalCacheDirs.firstOrNull()?.let {
                            File(it, context.getString(R.string.app_name)).apply { mkdirs() }
                        }
                        recording = startRecordingVideo(
                            context = context,
                            filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                            videoCapture = videoCapture,
                            outputDirectory = if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir,
                            executor = executor,
                            getVideoUri = {
                                videoUri = it
                                Log.d("LOGGING", "Video URI: $it")
                            },
                            getVideoFile = {
                                videoFile = it
                            },
                            currentRecordingState = currentRecordingState,
                            audioEnabled = audioEnabled.value
                        ) { event ->
                            videoPlaybackTime = event.recordingStats.recordedDurationNanos / 1000000
                            if (videoPlaybackTime >= 60000L) {
                                recording?.stop()
                                recording?.close()
                                recording = null

                                currentRecordingState.value = RecordingState.STOPPED
                                recordingStarted.value = false
                                recordingPaused.value = true
                                videoPlaybackTime = 0L
                                recordingStopped = true
                                onContentCapture(videoUri, videoFile)
                            }
                        }
                    }
                } catch (e: UndeclaredThrowableException) {
                    val underlyingException = e.cause
                    // TODO: Log or handle the underlying exception
                    Log.d("LOGGING", "Exception: $underlyingException")
                }
            })
    }
}

fun startRecordingVideo(
    context: Context,
    filenameFormat: String,
    videoCapture: VideoCapture<Recorder>,
    outputDirectory: File,
    currentRecordingState: MutableState<RecordingState>,
    getVideoFile: (File) -> Unit = {},
    getVideoUri: (Uri) -> Unit,
    executor: Executor,
    audioEnabled: Boolean,
    consumer: Consumer<VideoRecordEvent>
): Recording {

    val videoFile = File.createTempFile(
        SimpleDateFormat(
            filenameFormat, Locale.US
        ).format(System.currentTimeMillis()), ".mp4"
    )
    val outputOptions = FileOutputOptions.Builder(videoFile).build()

    getVideoUri(Uri.fromFile(videoFile))
    getVideoFile(videoFile)

    return videoCapture.output.prepareRecording(context, outputOptions).apply {
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Call the record audio permission
            return@apply
        }
        if (audioEnabled) withAudioEnabled()
    }.start(executor, consumer)
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

enum class RecordingState {
    NOT_STARTED, RECORDING, PAUSED, STOPPED
}
@Composable
private fun CaptureButton(
    videoCapture: () -> Unit,
    currentRecordingState: MutableState<RecordingState>,
    onResumeVideoRecording: () -> Unit,
    onPauseVideoRecording: () -> Unit,
    videoPlaybackTime: Long,
    mode: ContentCreationMode,
    modifier: Modifier,
) {
    val color = when (mode) {
        ContentCreationMode.VIDEO -> DeepRed
        ContentCreationMode.PHOTO -> Color.White
        else -> Color.White
    }
    val totalDuration = 60000L // 1 minute in milliseconds
    val animatedSweepAngle by animateFloatAsState(
        targetValue = (videoPlaybackTime % totalDuration) * 360f / totalDuration,
        animationSpec = tween(durationMillis = 1),
        label = ""
    )
    val currentlyRecording = currentRecordingState.value == RecordingState.RECORDING
    val currentlyPaused = currentRecordingState.value == RecordingState.PAUSED
    IconButton(onClick = {
        when (mode) {
            ContentCreationMode.VIDEO -> {
                when (currentRecordingState.value) {
                    RecordingState.NOT_STARTED -> {
                        videoCapture()
                    }

                    RecordingState.RECORDING -> {
                        onPauseVideoRecording()
                    }

                    RecordingState.PAUSED -> {
                        onResumeVideoRecording()
                    }

                    RecordingState.STOPPED -> {
                        // Do nun
                    }
                }
            }

            ContentCreationMode.PHOTO -> {

            }

            else -> {}
        }
    }) {
        Box(
            modifier = modifier.size(80.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent, shape = CircleShape)
                    .padding(4.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = Color.Transparent, radius = size.minDimension / 2, center = center
                    )

                    val outerCircleRadius = size.minDimension / 2 - 2.dp.toPx()
                    val innerCircleRadius = size.minDimension / 2 - 7.dp.toPx()

                    drawArc(
                        color = Color.White,
                        startAngle = -90f,
                        sweepAngle = if (currentlyRecording || currentlyPaused) animatedSweepAngle else 360f,
                        useCenter = false,
                        style = Stroke(3.dp.toPx()),
                        size = Size(outerCircleRadius * 2, outerCircleRadius * 2),
                        topLeft = Offset(center.x - outerCircleRadius, center.y - outerCircleRadius)
                    )

                    if (currentlyRecording) {
                        val squareSize = innerCircleRadius * 1.25f
                        drawRoundRect(
                            color = color,
                            topLeft = Offset(center.x - squareSize / 2, center.y - squareSize / 2),
                            size = Size(squareSize, squareSize),
                            cornerRadius = CornerRadius(10.dp.toPx())
                        )
                    } else {
                        drawCircle(
                            color = color, radius = innerCircleRadius, center = center
                        )
                    }
                }
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
    hasVideoPermission: Boolean,
    hasAudioPermission: Boolean,
    hasCameraPermission: Boolean,
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

    val buttonText = when {
        !hasCameraPermission -> {
            stringResource(id = R.string.grant_permission_camera)
        }

        !hasAudioPermission -> {
            stringResource(id = R.string.grant_permission_audio)
        }

        !hasVideoPermission -> {
            stringResource(id = R.string.grant_video_permission)
        }

        else -> {
            stringResource(id = R.string.all_permissions_granted)
        }
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
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = stringResource(
                            id = R.string.go_back
                        )
                    )
                }
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
                    text = buttonText, style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
