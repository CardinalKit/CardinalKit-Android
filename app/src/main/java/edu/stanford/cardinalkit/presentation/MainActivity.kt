package edu.stanford.cardinalkit.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import edu.stanford.cardinalkit.presentation.login.LoginViewModel
import edu.stanford.cardinalkit.presentation.navigation.CKNavHost
import edu.stanford.cardinalkit.presentation.navigation.Screens

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply{
        }

        setContent {
            val navController = rememberAnimatedNavController()

            CKNavHost(
                navController = navController,
                startDestination = Screens.WelcomeScreen.route
            )

            // Check if user is authenticated, if so, redirect them
            // to the home screen.
            val viewModel by viewModels<LoginViewModel>()
            viewModel.getAuthStatus()
            if(viewModel.isAuthenticated) {
                navController.navigate(Screens.MainScreen.route)
            }

        }
    }
}