package com.example.smstest.ui.home

import android.Manifest
import android.content.Context
import android.os.Build
import android.telephony.SmsManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HomeScreen() {
    Column {
        val context = LocalContext.current
        var text by remember { mutableStateOf("") }
        var number by remember { mutableStateOf("") }
        GetPermissions(context = context)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            placeholder = { Text(text = "Texto del SMS") }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = number, 
            onValueChange = { newNumber -> 
                number = newNumber
            },
            placeholder = { Text("Numero de telefono") }
        )

        Button(
            onClick = { SendSMS(phoneNumber = number, message = text, context = context) },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Enviar SMS")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
fun SendSMS(phoneNumber: String, message: String, context: Context) {
    try {
        val smsManager =
            context.getSystemService(SmsManager::class.java).createForSubscriptionId(1)
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)

        Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Error in sendind message", Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GetPermissions(context: Context) {
    val multiplePermissions = rememberMultiplePermissionsState(permissions =
        listOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_NUMBERS
        )
    )
    DisposableEffect(key1 = multiplePermissions) {
        multiplePermissions.launchMultiplePermissionRequest()
        onDispose {  }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}