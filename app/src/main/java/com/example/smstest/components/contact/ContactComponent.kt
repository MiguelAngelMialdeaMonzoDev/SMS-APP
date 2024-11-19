package com.example.smstest.components.contact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Contact(
    contactData: ContactData
) {
    Row {
        Text(
            modifier = Modifier.align(Alignment.CenterVertically).padding(8.dp),
            text = contactData.name.substring(0,1)
        )
        Column(modifier = Modifier.padding(4.dp)) {
            Text(text = contactData.name)
            Text(text = contactData.number)
        }
        Checkbox(
            checked = contactData.checked,
            onCheckedChange = { check -> contactData.checked = check}
        )
    }
}

@Preview
@Composable
fun ContactPreview() {
    val data = ContactData(
        name = "Jose Carlos",
        number = "617 98 36 37"
    )
    Contact(contactData = data)
}