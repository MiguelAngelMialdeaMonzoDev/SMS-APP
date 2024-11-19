package com.example.smstest.ui.contacts

import androidx.lifecycle.ViewModel
import com.example.smstest.components.contact.ContactData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ContactsViewModel: ViewModel() {
    private val _contactsState = MutableSharedFlow<List<ContactData>>()
    var contactsState = _contactsState.asSharedFlow()

    init {
        getContactsList()
    }

    private fun getContactsList() {

    }
}