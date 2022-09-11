package edu.stanford.cardinalkit.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.navigation.Screens
import edu.stanford.cardinalkit.presentation.onboarding.DisplayList
import edu.stanford.cardinalkit.presentation.onboarding.data

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewConsent(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screens.ProfileScreen.route)
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back icon")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors()
            )
        },
        content = { padding ->
            Column() {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.88f)
                        .padding(padding)
                        .padding(horizontal = 20.dp)
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.review_screen_title),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Light
                    )
                    DisplayList(
                        items = data
                    )
                }
            }
        }
    )
}
