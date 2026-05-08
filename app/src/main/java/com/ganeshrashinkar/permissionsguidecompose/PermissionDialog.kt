package com.ganeshrashinkar.permissionsguidecompose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermissionDeclined: Boolean,
    onDismiss:()-> Unit,
    onClick:() -> Unit,
    onGoToAppSettingsClick:()-> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {

        },
        confirmButton = {
            Column(Modifier.fillMaxWidth()) {
                HorizontalDivider()
                Text(
                    text = if(isPermissionDeclined){
                        "Grant Permission"
                    }
                    else{
                        "OK"
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermissionDeclined) {
                                onGoToAppSettingsClick()
                            } else {
                                onClick()
                            }
                        }
                        .padding(16.dp)
                )
            }
        },
        title = {
            Text(permissionTextProvider.getDescription(isPermissionDeclined))
        },
        modifier = modifier
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermissionPermenantlyDeclined: Boolean): String
}

class CameraPermissionTextProvider: PermissionTextProvider{
    override fun getDescription(isPermissionPermenantlyDeclined: Boolean): String {
        return if(isPermissionPermenantlyDeclined) {
            "It seems you permanently declined camera permission" +
                    " You can go to the app settings to enable it"
        }
            else{
                "This app needs access to your camera so that your" +
                        " friends can see you in call."
            }

    }

}

class RecordAudioPermissionTextProvider: PermissionTextProvider{
    override fun getDescription(isPermissionPermenantlyDeclined: Boolean): String {
        return if(isPermissionPermenantlyDeclined) {
            "It seems you permanently declined microphone permission" +
                    " You can go to the app settings to enable it"
        }
        else{
            "This app needs access to your microphone so that your" +
                    " friends can hear you in call."
        }

    }

}

class PhoneCallPermissionTextProvider: PermissionTextProvider{
    override fun getDescription(isPermissionPermenantlyDeclined: Boolean): String {
        return if(isPermissionPermenantlyDeclined) {
            "It seems you permanently declined phone calling permission" +
                    " You can go to the app settings to enable it"
        }
        else{
            "This app needs phone calling permission so that you can to with your friends"

        }

    }

}