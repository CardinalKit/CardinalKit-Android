package edu.stanford.cardinalkit.presentation

import android.os.Bundle

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.ContentView

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import edu.stanford.cardinalkit.presentation.login.LoginViewModel
import edu.stanford.cardinalkit.presentation.navigation.CKNavHost
import edu.stanford.cardinalkit.presentation.navigation.Screens

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
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
