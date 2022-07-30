package edu.stanford.cardinalkit.presentation.Onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.navigation.Screens

@Composable
fun Review(
    navController: NavHostController,
)
{
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
                navigationIcon={
                    IconButton(onClick={
                        navController.navigate(Screens.OnboardingScreen.route)
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
                Column(modifier= Modifier
                    .fillMaxHeight(0.88f)
                    .padding(horizontal = 20.dp)
                    .padding(top = 10.dp)
                ){
                    Text(
                        text = stringResource(R.string.review_screen_title),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Light
                    )
                    DisplayList(items = data)
                }
                Column(
                    modifier=Modifier.padding(top=6.dp),
                    verticalArrangement=Arrangement.Bottom){
                    GetStartedButton(
                        modifier = Modifier,
                    ) {
                        navController.navigate(Screens.SignInMethod.route)
                    }
                }
            }
        }
    )
}

@Composable
fun ReviewScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = onBoardingPage.title,
            fontSize=16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = onBoardingPage.description,
            fontSize=16.sp,
            textAlign = TextAlign.Left
        )
    }
}

@Composable
fun GetStartedButton(
    modifier: Modifier,
    onClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
    ) {
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Gray,
                    backgroundColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.continue_to_sign_in_button),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 30.dp)
                )

        }
    }
}

val data = listOf(
    OnBoardingPage.Content,
    OnBoardingPage.First,
    OnBoardingPage.Second,
    OnBoardingPage.Third,
    OnBoardingPage.Fourth
)
@Composable
fun DisplayList(items: List<OnBoardingPage>) {
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier,
        state = listState) {
        items(items){
            item -> ReviewScreen(onBoardingPage = item)
        }
    }
}


