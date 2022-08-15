package edu.stanford.cardinalkit.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import edu.stanford.cardinalkit.presentation.Onboarding.OnboardingScreen
import edu.stanford.cardinalkit.presentation.Onboarding.Review
import edu.stanford.cardinalkit.presentation.Onboarding.Signature
import edu.stanford.cardinalkit.presentation.Onboarding.WelcomeScreen
import edu.stanford.cardinalkit.presentation.RegisterScreen
import edu.stanford.cardinalkit.presentation.contacts.ContactsScreen
import edu.stanford.cardinalkit.presentation.home.HomeScreen
import edu.stanford.cardinalkit.presentation.login.ForgotPassword
import edu.stanford.cardinalkit.presentation.login.LoginScreen
import edu.stanford.cardinalkit.presentation.login.RegisterMethod
import edu.stanford.cardinalkit.presentation.login.SignInMethod
import edu.stanford.cardinalkit.presentation.main.MainScreen
import edu.stanford.cardinalkit.presentation.profile.ProfileScreen
import edu.stanford.cardinalkit.presentation.profile.ReviewConsent
import edu.stanford.cardinalkit.presentation.tasks.TasksScreen
import edu.stanford.cardinalkit.presentation.welcome.JoinStudyScreen

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun CKNavHost(navController: NavHostController, startDestination: String) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(Screens.OnboardingScreen.route) {
            OnboardingScreen(navController = navController)
        }
        composable(Screens.WelcomeScreen.route) {
            WelcomeScreen(navController = navController)
        }
        composable(Screens.Review.route) {
            Review(navController = navController)
        }
        composable(Screens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screens.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screens.RegisterMethod.route) {
            RegisterMethod(navController = navController)
        }
        composable(Screens.MainScreen.route) {
            MainScreen()
        }
        composable(Screens.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(Screens.TasksScreen.route) {
            TasksScreen()
        }
        composable(Screens.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screens.ContactsScreen.route) {
            ContactsScreen()
        }
        composable(Screens.ReviewConsent.route) {
            ReviewConsent(navController = navController)
        }
        composable(Screens.SignatureScreen.route){
            Signature(navController = navController)
        }
        composable(Screens.SignInMethod.route) {
            SignInMethod(navController = navController)
        }
        composable(Screens.JoinStudyScreen.route) {
            JoinStudyScreen(navController = navController)
        }
        composable(Screens.ForgotPasswordScreen.route) {
            ForgotPassword(navController = navController)
        }
    }
}