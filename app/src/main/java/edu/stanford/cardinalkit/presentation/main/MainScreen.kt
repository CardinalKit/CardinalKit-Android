package edu.stanford.cardinalkit.presentation.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import edu.stanford.cardinalkit.presentation.main.components.BottomNavigationBar
import edu.stanford.cardinalkit.presentation.navigation.CKNavHost
import edu.stanford.cardinalkit.presentation.navigation.Screens

@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = rememberAnimatedNavController()

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        content = {
            CKNavHost(navController, startDestination = Screens.HomeScreen.route)
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                modifier = Modifier.background(Color.White),
            )
        }
    )
}