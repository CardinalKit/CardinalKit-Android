package edu.stanford.cardinalkit.presentation.navigation

sealed class Screens(val route: String) {
    object MainScreen: Screens("Main")
    object HomeScreen: Screens("Home")
    object TasksScreen: Screens("Tasks")
    object LoginScreen: Screens("Login")
    object ProfileScreen: Screens("Profile")
    object ContactsScreen: Screens(route = "Contacts")
}