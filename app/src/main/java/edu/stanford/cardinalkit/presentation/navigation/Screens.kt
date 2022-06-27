package edu.stanford.cardinalkit.presentation.navigation

import edu.stanford.cardinalkit.common.Constants

sealed class Screens(val route: String) {
    object MainScreen: Screens(Constants.MAIN_SCREEN)
    object HomeScreen: Screens(Constants.HOME_SCREEN)
    object TasksScreen: Screens(Constants.TASKS_SCREEN)
    object LoginScreen: Screens(Constants.LOGIN_SCREEN)
    object ProfileScreen: Screens(Constants.PROFILE_SCREEN)
    object ContactsScreen: Screens(Constants.CONTACTS_SCREEN)
}