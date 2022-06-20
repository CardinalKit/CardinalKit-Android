package edu.stanford.cardinalkit.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import edu.stanford.cardinalkit.presentation.login.LoginScreen
import edu.stanford.cardinalkit.presentation.home.HomeScreen
import edu.stanford.cardinalkit.presentation.tasks.TasksScreen
import edu.stanford.cardinalkit.presentation.profile.ProfileScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import edu.stanford.cardinalkit.presentation.main.MainScreen
import edu.stanford.cardinalkit.presentation.contacts.ContactsScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CKNavHost(navController: NavHostController, startDestination: String) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(Screens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screens.MainScreen.route) {
            MainScreen()
        }
        composable(Screens.HomeScreen.route){
            HomeScreen()
        }
        composable(Screens.TasksScreen.route){
            TasksScreen()
        }
        composable(Screens.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screens.ContactsScreen.route){
            ContactsScreen()
        }
    }
}