package edu.stanford.cardinalkit.presentation.main.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import edu.stanford.cardinalkit.presentation.navigation.Screens

object TabBarItems {
    val BarItems = listOf(
        TabBarItem(
            title = "Home",
            image = Icons.Filled.Home,
            route = Screens.HomeScreen.route,
        ),
        TabBarItem(
            title = "Tasks",
            image = Icons.Filled.CheckBox,
            route = Screens.TasksScreen.route,
        ),
        TabBarItem(
            title = "Contacts",
            image = Icons.Filled.Contacts,
            route = Screens.ContactsScreen.route

        ),
        TabBarItem(
            title = "Profile",
            image = Icons.Filled.Person,
            route = Screens.ProfileScreen.route
        )
    )
}
