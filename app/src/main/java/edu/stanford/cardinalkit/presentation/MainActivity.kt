package edu.stanford.cardinalkit.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import edu.stanford.cardinalkit.presentation.login.LoginScreen
import edu.stanford.cardinalkit.presentation.login.LoginViewModel
import edu.stanford.cardinalkit.presentation.main.MainScreen
import edu.stanford.cardinalkit.presentation.navigation.CKNavHost
import edu.stanford.cardinalkit.presentation.navigation.Screens

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val userState by viewModels<LoginViewModel>()

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
            // to the home screen, otherwise send them to the login screen
            userState.getAuthStatus()
            if(userState.isAuthenticated) {
                MainScreen()
            } else {
                LoginScreen(navController = navController)
            }

        }
    }
}