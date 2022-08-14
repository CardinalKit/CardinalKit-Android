package edu.stanford.cardinalkit.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import edu.stanford.cardinalkit.presentation.Onboarding.DisplayList
import edu.stanford.cardinalkit.presentation.Onboarding.data
import edu.stanford.cardinalkit.presentation.navigation.Screens


@Composable
fun ReviewConsent(
    navController: NavHostController
)
{
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
                navigationIcon={
                    IconButton(onClick={
                        navController.navigate(Screens.ProfileScreen.route)
                    }){
                        Icon(Icons.Filled.ArrowBack, "back Icon")
                    }
                },
                backgroundColor = Color.White,
                contentColor = Color.Black,
                elevation = 0.dp
            )
        },
        content = {
            Column() {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.88f)
                        .padding(horizontal = 20.dp)
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = "Review",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Light
                    )
                    DisplayList(
                        items = data
                    )

                }
            }

})}



