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
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.stanford.cardinalkit.presentation.health.HealthViewModel
import edu.stanford.cardinalkit.presentation.login.LoginViewModel
import edu.stanford.cardinalkit.presentation.navigation.CKNavHost
import edu.stanford.cardinalkit.presentation.navigation.Screens
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply {}

        /**
         * Gets permissions for accessing health data from Health Connect
         * if not already provided.
         */
        val healthViewModel by viewModels<HealthViewModel>()
        val requestPermissionsActivityContract = healthViewModel.healthConnectManager.healthConnectClient
            .permissionController
            .createRequestPermissionActivityContract()

        val requestPermissions = registerForActivityResult(requestPermissionsActivityContract){ granted ->
            healthViewModel.updatePermissionsStatus(granted.containsAll(healthViewModel.permissions))
        }

        fun checkPermissionsAndRun() {
            val permissions = healthViewModel.permissions
            lifecycleScope.launch {
                val granted = healthViewModel
                    .healthConnectManager
                    .healthConnectClient
                    .permissionController
                    .getGrantedPermissions(permissions)
                if (!granted.containsAll(permissions)) {
                    requestPermissions.launch(permissions)
                } else {
                    healthViewModel.updatePermissionsStatus(true)
                }
            }
        }

        setContent {
            val navController = rememberAnimatedNavController()
            val isAuthenticated = remember { mutableStateOf(false) }

            /**
             * Observe authentication status - redirect to register/login screen
             * if not authenticated, or main screen if authenticated.
             */
            val loginViewModel by viewModels<LoginViewModel>()

            loginViewModel.getAuthStatus().observe(this) {
                isAuthenticated.value = it
            }

            if(isAuthenticated.value) {
                // Get health data permissions if needed
                checkPermissionsAndRun()

                // Navigate to main screen
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
