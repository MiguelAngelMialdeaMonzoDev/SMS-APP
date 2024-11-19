package com.example.smstest.components.contact

data class ContactData(
    val name: String,
    val number: String,
    var checked: Boolean = false
)