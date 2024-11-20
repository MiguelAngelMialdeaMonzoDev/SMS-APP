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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
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
fun HomeScreen(viewModel: HomeViewModel) {
    Column {
        val context = LocalContext.current
        var text by remember { mutableStateOf("") }
        var number by remember { mutableStateOf("") }
        val pattern = remember { Regex("^\\d+\$") }
        val list by viewModel.phoneNumbers.collectAsState()
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
                if (newNumber.isEmpty() || newNumber.matches(pattern)) {
                    number = newNumber
                }
            },
            placeholder = { Text("Número de teléfono") }
        )

        Button(
            onClick = {
                viewModel.addPhone(phoneNumber = number)
                number = ""
                Toast.makeText(context, viewModel.phoneNumbers.value.get(0), Toast.LENGTH_SHORT).show()
                      },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            enabled = number.SpainNumberComprobation()
        ) {
            Text(text = "Añadir teléfonos")
        }

        Button(
            onClick = {
                SendSMS(phoneNumbers = viewModel.phoneNumbers.value.ifEmpty { listOf(number) }, message = text, context = context)
                viewModel.removeAll()
                      },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            enabled = (viewModel.phoneNumbers.value.isNotEmpty() || number.SpainNumberComprobation()) && text.isNotEmpty()
        ) {
            Text(text = "Enviar SMS")
        }

        LazyColumn {
            items(list) { item ->
                Text(text = item)
            }
        }
    }
}

fun String.SpainNumberComprobation(): Boolean {
    return this.length == 9
}

@RequiresApi(Build.VERSION_CODES.S)
fun SendSMS(phoneNumbers: List<String>, message: String, context: Context) {
    try {
        phoneNumbers.forEach { number ->
            val smsManager =
                context.getSystemService(SmsManager::class.java).createForSubscriptionId(1)
            smsManager.sendTextMessage(number, null, message, null, null)
        }


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
    HomeScreen(HomeViewModel())
}