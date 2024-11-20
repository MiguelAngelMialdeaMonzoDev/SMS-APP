package com.example.smstest.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _phoneNumbers = MutableStateFlow<List<String>>(emptyList())
    val phoneNumbers: StateFlow<List<String>> = _phoneNumbers

    fun addPhone(phoneNumber: String) {
        viewModelScope.launch {
            _phoneNumbers.value = _phoneNumbers.value + phoneNumber
        }
    }

    fun removeAll() {
        viewModelScope.launch {
            _phoneNumbers.value = emptyList()
        }
    }
}