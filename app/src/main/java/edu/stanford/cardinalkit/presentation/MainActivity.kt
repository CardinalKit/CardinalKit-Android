package edu.stanford.cardinalkit.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import edu.stanford.cardinalkit.presentation.login.LoginViewModel
import edu.stanford.cardinalkit.presentation.navigation.CKNavHost
import edu.stanford.cardinalkit.presentation.navigation.Screens

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply {}

        setContent {

            val navController = rememberAnimatedNavController()
            val isAuthenticated = remember { mutableStateOf(false) }

            val viewModel by viewModels<LoginViewModel>()

            // start auth listener
            viewModel.getAuthStatus().observe(this) {
                isAuthenticated.value = it
            }

            if(isAuthenticated.value) {
                CKNavHost(
                    navController = navController,
                    startDestination = Screens.MainScreen.route
                )
            } else {
                CKNavHost(
                    navController = navController,
                    startDestination = Screens.JoinStudyScreen.route
                )
            }

        }
    }
}
