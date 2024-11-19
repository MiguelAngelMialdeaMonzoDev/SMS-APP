package com.example.smstest.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smstest.ui.contacts.ContactsScreen
import com.example.smstest.ui.home.HomeScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeScreen) {
        composable<HomeScreen> { HomeScreen() }
        composable<ContactScreen> { ContactsScreen() }
    }
}