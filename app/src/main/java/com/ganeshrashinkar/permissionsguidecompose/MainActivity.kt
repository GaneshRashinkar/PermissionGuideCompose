package com.ganeshrashinkar.permissionsguidecompose

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ganeshrashinkar.permissionsguidecompose.ui.theme.PermissionsGuideComposeTheme

class MainActivity : ComponentActivity() {
    val permissionsToRequest = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CALL_PHONE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PermissionsGuideComposeTheme {
            val viewModel = viewModel<MainViewModel>()
            val dialogQueue = viewModel.visiblePermissionDialogQueue
            val cameraPermissionLauncher = rememberLauncherForActivityResult (
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    viewModel.onPermissionResult(Manifest.permission.CAMERA,
                        isGranted)
                }
            )
                val multiplePermissionLauncher = rememberLauncherForActivityResult (
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { perms ->
                        permissionsToRequest.forEach {
                            permission->
                            viewModel.onPermissionResult(permission,perms[permission] == true)
                        }
                    }
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Button({
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }) {
                        Text("Request One Permission")
                    }
                    Spacer(Modifier.height(16.dp))
                    Button({
                        multiplePermissionLauncher.launch(
                            permissionsToRequest
                        )
                    }) {
                        Text("Request Multiple Permissions")
                    }
                }

                dialogQueue
                    .reversed()
                    .forEach {
                        permission->
                        PermissionDialog(
                            permissionTextProvider = when(permission){
                                Manifest.permission.CAMERA -> {
                                    CameraPermissionTextProvider()
                                }
                                Manifest.permission.RECORD_AUDIO -> {
                                    RecordAudioPermissionTextProvider()
                                }
                                Manifest.permission.CALL_PHONE -> {
                                    PhoneCallPermissionTextProvider()
                                }
                                else -> return@forEach
                            },
                            isPermissionDeclined = !shouldShowRequestPermissionRationale(
                                permission
                            ),
                            onDismiss = viewModel::dismissDialog,
                            onClick = {
                                viewModel.dismissDialog()
                                multiplePermissionLauncher.launch(arrayOf(
                                    permission
                                ))
                            },
                            onGoToAppSettingsClick = ::openSettings,

                        )
                    }
            }
        }
    }
}

fun Activity.openSettings(){
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package",packageName,null)
    ).also(::startActivity)
}

