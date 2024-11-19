package com.example.smstest.ui.contacts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.smstest.components.contact.Contact

@Composable
fun ContactsScreen(viewModel: ContactsViewModel) {
    val contacts by viewModel.contactsState.collectAsState(initial = listOf())
    LazyColumn {
        items(contacts) { item ->
            Contact(contactData = item)
        }
    }
}

@Preview
@Composable
fun ContactsScreenPreview() {
    ContactsScreen()
}