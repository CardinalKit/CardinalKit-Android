package edu.stanford.cardinalkit.presentation.onboarding

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun WelcomeScreen(
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screens.JoinStudyScreen.route)
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back icon")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors()
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    text = stringResource(R.string.welcome_title),
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp)
                        .padding(top = 10.dp),
                    text = stringResource(R.string.welcome_description),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Left
                )
                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .fillMaxHeight(0.47f),
                    painter = painterResource(R.drawable.welcome),
                    contentDescription = "Welcome Image"
                )
                Box(
                    modifier = Modifier
                        .padding(top = 40.dp)
                ) {
                    ReviewButton(
                        modifier = Modifier
                    ) {
                        navController.navigate(Screens.OnboardingScreen.route)
                    }
                }
            }
        }
    )
}

@Composable
fun ReviewButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = stringResource(R.string.get_started_button),
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 7.dp, horizontal = 90.dp)
            )
        }
    }
}
